package com.ahmetozaydin.ecommerceapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmetozaydin.ecommerceapp.data.Cart
import com.ahmetozaydin.ecommerceapp.data.CartDatabase
import com.ahmetozaydin.ecommerceapp.data.Favorite
import com.ahmetozaydin.ecommerceapp.data.FavoriteDatabase
import com.ahmetozaydin.ecommerceapp.fragment.FavoriteFragment
import kotlinx.coroutines.*

class FavoriteFragmentViewModel : ViewModel() {
    private var _isListEmpty = MutableLiveData<Boolean>()
    val isListEmpty: LiveData<Boolean> get() = _isListEmpty
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading get() = _isLoading
    private var _favorites = MutableLiveData<List<Favorite>>()
    val favorites get() = _favorites




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


}