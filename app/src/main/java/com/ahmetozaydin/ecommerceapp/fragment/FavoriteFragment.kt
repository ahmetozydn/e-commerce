package com.ahmetozaydin.ecommerceapp.fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.ecommerceapp.adapter.CartAdapter
import com.ahmetozaydin.ecommerceapp.adapter.FavoriteAdapter
import com.ahmetozaydin.ecommerceapp.data.CartDatabase
import com.ahmetozaydin.ecommerceapp.data.Favorite
import com.ahmetozaydin.ecommerceapp.data.FavoriteDatabase
import com.ahmetozaydin.ecommerceapp.databinding.FragmentFavoriteBinding
import com.ahmetozaydin.ecommerceapp.model.Product
import com.ahmetozaydin.ecommerceapp.utils.SwipeHelper
import com.ahmetozaydin.ecommerceapp.view.ProductDetailsActivity
import com.ahmetozaydin.ecommerceapp.viewmodel.FavoriteFragmentViewModel
import kotlinx.coroutines.*


class FavoriteFragment : Fragment() , FavoriteAdapter.Listener{
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
        favoriteDatabase = context?.let { FavoriteDatabase.invoke(it) }
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getDataFromRoom(requireContext())

        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(binding.favoriteRecyclerView) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                val buttons = ArrayList<UnderlayButton>()
                val deleteButton = deleteButton(position)
                val addToCartButton = addToCartButton(position)

                runBlocking {
                    Log.i(TAG, "instantiateUnderlayButton: inside runblocking")
                    CoroutineScope(Dispatchers.IO).launch {
                        if(position != -1){

                        }
                        val isAddedToCart = favoriteAdapter?.getItemInfo(position)?.let {
                            CartDatabase.invoke(context = requireContext()).cartDao().searchEntity(it)
                        }

                        Log.i(TAG, "instantiateUnderlayButton: the position is $position")

                        if(isAddedToCart !=null){
                            Log.i(TAG, "instantiateUnderlayButton: $isAddedToCart")
                            buttons.add(deleteButton)
                        }else{
                            Log.i(TAG, "instantiateUnderlayButton: $isAddedToCart")
                            buttons.add(deleteButton)
                            buttons.add(addToCartButton)
                        }
                        Log.i(TAG, "coroutine scope: $isAddedToCart ")
                    }
                }
                return buttons
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.favoriteRecyclerView)
        liveDataObserver()
    }

    private fun initializeRecyclerView(favorites: List<Favorite>) {
        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.favoriteRecyclerView.layoutManager = layoutManager
        favoriteAdapter = FavoriteAdapter(favorites as ArrayList<Favorite>, requireContext())
        binding.favoriteRecyclerView.adapter = favoriteAdapter
    }

    private fun liveDataObserver() {
        viewModel.isListEmpty.observe(viewLifecycleOwner, Observer { isEmpty ->
            if (isEmpty) {
                binding.emptyList.visibility = View.VISIBLE
            } else {
                binding.emptyList.visibility = View.INVISIBLE
            }
        })
        viewModel.favorites.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.INVISIBLE
            if (it.isEmpty()) {
                binding.emptyList.visibility = View.VISIBLE
            } else {
                initializeRecyclerView(it)
                binding.emptyList.visibility = View.INVISIBLE
            }
        })
        viewModel.product.observe(viewLifecycleOwner, Observer {
            it.let {

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
                    val id = favoriteAdapter!!.getItemInfo(position)
                        if (id != null) {
                            viewModel.addToCart(requireContext(), id = id)
                        }
                }
            })
    }

    override fun onItemClick(products: Product) {
        favoriteList.forEach {
            if(it.id == products.id){
                var item = it.id
            }
        }
        val intent = Intent(context, ProductDetailsActivity::class.java)
        intent.putExtra("product", favoriteList)
        context?.startActivity(intent)
    }
}