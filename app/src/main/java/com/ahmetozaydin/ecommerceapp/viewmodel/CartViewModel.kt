package com.ahmetozaydin.ecommerceapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ahmetozaydin.ecommerceapp.adapter.CartAdapter
import com.ahmetozaydin.ecommerceapp.data.Cart
import com.ahmetozaydin.ecommerceapp.data.CartDatabase
import com.ahmetozaydin.ecommerceapp.databinding.FragmentCartBinding
import kotlinx.coroutines.*

class CartViewModel : ViewModel() {
    val cartList = MutableLiveData<ArrayList<Cart>>()
    val _cartList get() = cartList
    val isCartListLoading = MutableLiveData<Boolean>()
    val isErrorOccurred = MutableLiveData<Boolean>()
    val tempList = ArrayList<Cart>()
     var isCartListEmpty = MutableLiveData<Boolean>()
    var totalAmounth = MutableLiveData<Int>()
    private lateinit var cartAdapter: CartAdapter
    fun getDataFromRoom(
        context: Context,
        binding: FragmentCartBinding,
        cartDatabase: CartDatabase
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            cartDatabase.cartDao().getAllEntities().forEach {
                tempList.add(it) //TODO)
            }
        }
        cartList.value = tempList
        isCartListLoading.value = false
        isErrorOccurred.value = false
    }
    fun removeItemFromRoom(position: Int, cartDatabase: CartDatabase) {
        CoroutineScope(Dispatchers.IO).launch {

            isCartListEmpty.postValue(false)
            val database = cartDatabase.cartDao()
            database.delete(position)
            if (database.rowCount() == 0){
                    isCartListEmpty.postValue(true)
            }
            else{
                isCartListEmpty.postValue(false)
            }
        }
    }
    fun getIsCartListEmpty(): Boolean? {
        return isCartListEmpty.value
    }
    fun calculateTotalAmounth(cartDatabase: CartDatabase){
        totalAmounth.value = 0
        var total = 0
        CoroutineScope(Dispatchers.IO).launch(){
            val cartList = cartDatabase.cartDao().getAllEntities()
            cartList.forEach{
                total = it.price!!.times(it.quantity)
            }
        }
        totalAmounth.postValue(total)
    }
}