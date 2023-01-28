package com.ahmetozaydin.ecommerceapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Image::class], version = 1)
abstract class ImageDatabase : RoomDatabase(){
    abstract fun imageDao() : ImageDao
    companion object{
        //Singleton
        @Volatile private var instance : ImageDatabase? = null
        private val lock = Any()
        operator fun invoke(context : Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }
        private fun makeDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,ImageDatabase::class.java,"image"
        ).build()
    }
}
