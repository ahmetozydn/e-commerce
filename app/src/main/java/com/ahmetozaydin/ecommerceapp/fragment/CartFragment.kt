package com.ahmetozaydin.ecommerceapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.ecommerceapp.adapter.CartAdapter
import com.ahmetozaydin.ecommerceapp.data.CartDatabase
import com.ahmetozaydin.ecommerceapp.databinding.FragmentCartBinding
import com.ahmetozaydin.ecommerceapp.utils.SwipeHelper
import com.ahmetozaydin.ecommerceapp.viewmodel.CartViewModel


class CartFragment : Fragment() {
    private lateinit var viewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter
    private lateinit var binding: FragmentCartBinding
    private var cartDatabase : CartDatabase? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // binding.recyclerViewCart.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        viewModel = ViewModelProvider(this)[CartViewModel::class.java]
       /* viewModel.cartList.observe(viewLifecycleOwner) {
            it?.let {
                println("viewModel.observe")
                viewModel.getDataFromRoom(requireContext(), binding)
            }
        }*/
        var totalAmount = 0
        cartDatabase = CartDatabase.invoke(requireContext())
        viewModel.getDataFromRoom(requireContext(),binding, cartDatabase!!)
        viewModel.cartList.observe(viewLifecycleOwner, Observer {
           /* cartAdapter = viewModel.cartList.let { context?.let { it1 ->
                it.value?.let { it2 ->
                    CartAdapter(
                        it2,
                        it1,cartDatabase)
                }
            } }!!*/
            //binding.recyclerViewCart.adapter = cartAdapter
            binding.progressBar.visibility = View.INVISIBLE
            binding.relativeLayoutFavorite.visibility = View.INVISIBLE
        })
        viewModel.cartList.value?.forEach {
            totalAmount += it.price!!
            println("totallll")
        }
        if(totalAmount != 0){//TODO()
            binding.totalAmount.text = totalAmount.toString()
        }
        println("total $totalAmount")
        viewModel.isCartListLoading.observe(viewLifecycleOwner, Observer {
        })
        setUpRecyclerView()
    }
    private fun setUpRecyclerView() {
        binding.recyclerViewCart.adapter =
            viewModel.cartList.value?.let {
                CartAdapter(
                    it,requireContext(),cartDatabase!!,listOf(
                        "Item 1: Delete"
                    ))
            }
        binding.recyclerViewCart.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        binding.recyclerViewCart.layoutManager = LinearLayoutManager(requireContext())

        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(binding.recyclerViewCart) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                var buttons = listOf<UnderlayButton>()
                val deleteButton = deleteButton(position)
                val markAsUnreadButton = markAsUnreadButton(position)
                val archiveButton = archiveButton(position)
                when (position) {
                    1 -> buttons = listOf(deleteButton)
                    2 -> buttons = listOf(deleteButton, markAsUnreadButton)
                    3 -> buttons = listOf(deleteButton, markAsUnreadButton, archiveButton)
                    else -> Unit
                }
                return buttons
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerViewCart)
    }

    private fun toast(text: String) {
        //toast.cancel()
        val toast = Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun deleteButton(position: Int) : SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireContext(),
            "Delete",
            14.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    toast("Deleted item $position")
                }
            })
    }

    private fun markAsUnreadButton(position: Int) : SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireContext(),
            "Mark as unread",
            14.0f,
            android.R.color.holo_green_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    toast("Marked as unread item $position")
                }
            })
    }

    private fun archiveButton(position: Int) : SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireContext(),
            "Archive",
            14.0f,
            android.R.color.holo_blue_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    toast("Archived item $position")
                }
            })
    }
    /*private fun setUpRecyclerView() {
        binding.recyclerViewCart.adapter =
            viewModel.cartList.value?.let {
                CartAdapter(
                    it,requireContext(),cartDatabase!!,listOf(
                        "Item 1: Delete"
                    ))
            }
        binding.recyclerViewCart.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        binding.recyclerViewCart.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)

        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(binding.recyclerViewCart) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                var buttons = listOf<UnderlayButton>()
                val deleteButton = deleteButton(position)
                buttons = listOf(deleteButton)
                return buttons
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewCart)
    }
    private fun deleteButton(position: Int) : SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireContext(),
            "Delete",
            14.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    Toast.makeText(requireContext(),"Deleted item $position",Toast.LENGTH_SHORT).show()
                }
            })
    }*/
}