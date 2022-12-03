package com.ahmetozaydin.ecommerceapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cart::class], version = 1)
abstract class CartDatabase : RoomDatabase(){
    abstract fun cartDao() : CartDao
    companion object{
        //Singleton
        @Volatile private var instance : CartDatabase? = null
        private val lock = Any()
        operator fun invoke(context : Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }
        private fun makeDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,CartDatabase::class.java,"cart"
        ).build()
    }
}