package com.ahmetozaydin.ecommerceapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase(){
abstract fun favoriteDao() : FavoriteDao
companion object{
    //Singleton
    @Volatile private var instance : FavoriteDatabase? = null
    private val lock = Any()
    operator fun invoke(context : Context) = instance ?: synchronized(lock) {
        instance ?: makeDatabase(context).also {
            instance = it
        }
    }
    private fun makeDatabase(context : Context) = Room.databaseBuilder(
        context.applicationContext,FavoriteDatabase::class.java,"favorite"
    ).build()
}
}