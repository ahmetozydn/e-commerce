package com.ahmetozaydin.ecommerceapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.ecommerceapp.adapter.CartAdapter
import com.ahmetozaydin.ecommerceapp.data.CartDatabase
import com.ahmetozaydin.ecommerceapp.databinding.FragmentCartBinding
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
        val layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        binding.recyclerViewCart.layoutManager = layoutManager
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
            cartAdapter = viewModel.cartList.let { context?.let { it1 ->
                it.value?.let { it2 ->
                    CartAdapter(
                        it2,
                        it1,cartDatabase)
                }
            } }!!
            binding.recyclerViewCart.adapter = cartAdapter
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
    }
}