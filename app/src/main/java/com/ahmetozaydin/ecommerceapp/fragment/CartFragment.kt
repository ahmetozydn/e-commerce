package com.ahmetozaydin.ecommerceapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmetozaydin.ecommerceapp.adapter.CartAdapter
import com.ahmetozaydin.ecommerceapp.data.CartDatabase
import com.ahmetozaydin.ecommerceapp.databinding.FragmentCartBinding
import com.ahmetozaydin.ecommerceapp.utils.SwipeHelper
import com.ahmetozaydin.ecommerceapp.viewmodel.CartViewModel
import kotlinx.coroutines.*
import kotlin.math.absoluteValue


class CartFragment : Fragment() {
    private lateinit var viewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter
    private lateinit var binding: FragmentCartBinding
    private var cartDatabase: CartDatabase? = null
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

        cartDatabase = CartDatabase.invoke(requireContext())
        viewModel.getDataFromRoom(requireContext(), binding, cartDatabase!!)
        /* if(viewModel.cartList.value?.isEmpty() == true){
             binding.emptyListMessage.visibility = View.VISIBLE
         }*/

        binding.progressBar.visibility = View.GONE
        viewModel.cartList.observe(viewLifecycleOwner, Observer {
            /* cartAdapter = viewModel.cartList.let { context?.let { it1 ->
                 it.value?.let { it2 ->
                     CartAdapter(
                         it2,
                         it1,cartDatabase)
                 }
             } }!!*/

            //binding.recyclerViewCart.adapter = cartAdapter
        })
        viewModel.calculateTotalAmounth(cartDatabase!!)
        viewModel.totalAmounth.observe(viewLifecycleOwner, Observer {
            if(it != 0){
                binding.totalAmount.text = it.toString()
            }
        })
        CoroutineScope(Dispatchers.IO).launch {
            if(cartDatabase!!.cartDao().rowCount() == 0){
                withContext(Dispatchers.Main){
                    binding.emptyListMessage.visibility = View.VISIBLE
                }

            }else{
                withContext(Dispatchers.Main){
                    binding.emptyListMessage.visibility = View.INVISIBLE
                }
            }
        }


        viewModel.isCartListEmpty.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.emptyListMessage.visibility = View.VISIBLE
            }else{
                binding.emptyListMessage.visibility = View.INVISIBLE
            }
        })

        viewModel.isCartListLoading.observe(viewLifecycleOwner, Observer {
        })
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

        cartAdapter = viewModel.cartList.value?.let {
            CartAdapter(it, requireContext())
        }!!
        binding.recyclerViewCart.adapter = cartAdapter
        binding.recyclerViewCart.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerViewCart.layoutManager = LinearLayoutManager(requireContext())

        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(binding.recyclerViewCart) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                val buttons = ArrayList<UnderlayButton>()
                val deleteButton = deleteButton(position)
                buttons.add(deleteButton)
                return buttons
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerViewCart)
    }

    private fun deleteButton(position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            requireContext(),
            "Delete",
            14.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {

                    cartDatabase?.let {
                        cartAdapter.getItemInfo(position)
                            ?.let { it1 -> viewModel.removeItemFromRoom(it1, it) }
                    }
                    cartAdapter.deleteItem(position)

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
    fun checkOutProducts(){

    }
    fun changeQuantity(increase : Boolean){
        if(increase){

        }
    }
}