package com.ahmetozaydin.ecommerceapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmetozaydin.ecommerceapp.data.CartDatabase
import com.ahmetozaydin.ecommerceapp.data.Favorite
import com.ahmetozaydin.ecommerceapp.data.FavoriteDatabase
import kotlinx.coroutines.*

class FavoriteFragmentViewModel :ViewModel() {
     var isListEmpty = MutableLiveData<Boolean>()

    fun removeItemFromRoom(position:Int,context:Context){
        CoroutineScope(Dispatchers.IO).
            launch {
                val favoriteDatabase = FavoriteDatabase.invoke(context)
                favoriteDatabase.favoriteDao().delete(position)
                if(favoriteDatabase.favoriteDao().rowCount() == 0){
                    withContext(Dispatchers.Main){ isListEmpty.value = true }
                }
            }
    }
    fun addItemToRoom(position: Int,context: Context){ //TODO
    /*    CoroutineScope(Dispatchers.IO).
                launch {
                    if()
                    val cartDatabase = CartDatabase.invoke(context)
                    cartDatabase.cartDao().insertEntity(cart)
                }*/
    }
}