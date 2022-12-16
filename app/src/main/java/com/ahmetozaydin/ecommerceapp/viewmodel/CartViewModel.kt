package com.ahmetozaydin.ecommerceapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmetozaydin.ecommerceapp.adapter.CartAdapter
import com.ahmetozaydin.ecommerceapp.data.Cart
import com.ahmetozaydin.ecommerceapp.data.CartDatabase
import com.ahmetozaydin.ecommerceapp.databinding.FragmentCartBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
     val cartList = MutableLiveData<ArrayList<Cart>>()
    val isCartListLoading = MutableLiveData<Boolean>()
     val isErrorOccurred = MutableLiveData<Boolean>()
     val tempList = ArrayList<Cart>()
    private lateinit var cartAdapter : CartAdapter
    fun getDataFromRoom(context:Context, binding: FragmentCartBinding,cartDatabase: CartDatabase){
        println("getDataFromRoom")
        GlobalScope.launch {
            cartDatabase.cartDao().getAllEntities().forEach{
                tempList.add(it) //TODO)
                println("x")
            }
        }
        cartList.value = tempList
        isCartListLoading.value = false
        isErrorOccurred.value = false
        println(cartList)
        println("size of list: "+ cartList.value?.size)
    }
}