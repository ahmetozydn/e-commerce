package com.ahmetozaydin.ecommerceapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImageDao {

    @Insert
    suspend fun insert(image : Image)
    @Query(value = "DELETE FROM image")
    suspend fun deleteAllRecords()
    @Query(value = "SELECT * FROM image")
    suspend fun getAllRecords(): List<Image>
    @Query("SELECT url FROM image WHERE product_id = :id")
    suspend fun getRecord(id : Int) : List<String>
}