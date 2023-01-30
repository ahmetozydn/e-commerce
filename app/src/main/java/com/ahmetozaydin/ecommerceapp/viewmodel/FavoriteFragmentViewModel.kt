package com.ahmetozaydin.ecommerceapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmetozaydin.ecommerceapp.data.*
import com.ahmetozaydin.ecommerceapp.fragment.FavoriteFragment
import com.ahmetozaydin.ecommerceapp.model.Product
import kotlinx.coroutines.*

class FavoriteFragmentViewModel : ViewModel() {
    private var _isListEmpty = MutableLiveData<Boolean>()
    val isListEmpty: LiveData<Boolean> get() = _isListEmpty
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading get() = _isLoading
    private var _favorites = MutableLiveData<List<Favorite>>()
    val favorites get() = _favorites
    private var _favorite = MutableLiveData<Favorite>()
    val product get() =  _favorite




    fun removeItemFromRoom(position: Int, context: Context) =
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteDatabase = FavoriteDatabase.invoke(context)
            favoriteDatabase.favoriteDao().delete(position)
            if (favoriteDatabase.favoriteDao().rowCount() == 0) {
                _isListEmpty.postValue(true)
            }
        }
    fun getDataFromRoom(context : Context) =
        viewModelScope.launch(Dispatchers.IO) {
            val db = FavoriteDatabase.invoke(context = context)
            _favorites.postValue(db.favoriteDao().getAllEntities())
    }

    fun addItemToRoom(position: Int, context: Context) { //TODO
        /*    CoroutineScope(Dispatchers.IO).
                    launch {
                        if()
                        val cartDatabase = CartDatabase.invoke(context)
                        cartDatabase.cartDao().insertEntity(cart)
                    }*/
    }
    fun addToCart(context : Context,id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val record = FavoriteDatabase.invoke(context).favoriteDao().getEntity(id)
            val aCart = Cart(record.id,record.title,record.discountPercentage,record.description,record.price,record.rating,record.stock,record.brand,record.thumbnail,true,1)
            val cartDb = CartDatabase(context).cartDao().insertEntity(aCart)

        }
    }
    fun isAddedToCart(context: Context,id:Int): Boolean{
        return false
    }


}