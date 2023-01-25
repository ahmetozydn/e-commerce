package com.ahmetozaydin.ecommerceapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.ecommerceapp.adapter.FavoriteAdapter
import com.ahmetozaydin.ecommerceapp.data.Favorite
import com.ahmetozaydin.ecommerceapp.data.FavoriteDatabase
import com.ahmetozaydin.ecommerceapp.databinding.FragmentFavoriteBinding
import com.ahmetozaydin.ecommerceapp.utils.SwipeHelper
import com.ahmetozaydin.ecommerceapp.viewmodel.CartViewModel
import com.ahmetozaydin.ecommerceapp.viewmodel.FavoriteFragmentViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private var favoriteAdapter: FavoriteAdapter? = null
    private var favoriteList = ArrayList<Favorite>()
    private var favoriteDatabase: FavoriteDatabase? = null
    private lateinit var viewModel: FavoriteFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FavoriteFragmentViewModel::class.java]
        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.favoriteRecyclerView.layoutManager = layoutManager
        favoriteDatabase = context?.let { FavoriteDatabase.invoke(it) }
        GlobalScope.
            launch {
                println("GlobalScope is worked")
                favoriteDatabase?.favoriteDao()?.getAllEntities()!!.forEach {
                    favoriteList.add(it)
                }
                activity?.runOnUiThread {
                    if (favoriteList.isEmpty()) {
                        println("is empty? " + favoriteList.size)
                        binding.relativeLayoutFavorite.visibility = View.VISIBLE
                        binding.favoriteRecyclerView.visibility = View.INVISIBLE
                    } else {
                        binding.relativeLayoutFavorite.visibility =
                            View.INVISIBLE// TODO(Can't create handler inside thread)
                        binding.favoriteRecyclerView.visibility = View.VISIBLE
                    }
                }

            }
        binding.progressBar.visibility = View.GONE
        favoriteAdapter = FavoriteAdapter(favoriteList, requireContext())
        binding.favoriteRecyclerView.adapter = favoriteAdapter

        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(binding.favoriteRecyclerView) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                val buttons = ArrayList<UnderlayButton>()
                val deleteButton = deleteButton(position)
                val addToCartButton = addToCartButton(position)
                buttons.add(deleteButton)
                buttons.add(addToCartButton)
                return buttons
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.favoriteRecyclerView)
        liveDataObserver()
    }

    private fun liveDataObserver() {
        viewModel.isListEmpty.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.textViewEmptyListMessage.visibility = View.VISIBLE
            } else {
                binding.textViewEmptyListMessage.visibility = View.GONE
            }
        })
    }

    private fun deleteButton(position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireContext(),
            "Delete",
            14.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    favoriteDatabase?.let {
                        favoriteAdapter?.getItemInfo(position)
                            ?.let { it1 -> viewModel.removeItemFromRoom(it1, requireContext()) }
                    }
                    favoriteAdapter?.deleteItem(position)
                }
            })
    }
    private fun addToCartButton(position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireContext(),
            "Add to Cart",
            14.0f,
            android.R.color.holo_blue_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    val idOfProduct = favoriteAdapter?.getItemInfo(position)
                    if (idOfProduct != null) {
                        viewModel.addItemToRoom(idOfProduct,requireContext())
                    }
                }
            })
    }
}