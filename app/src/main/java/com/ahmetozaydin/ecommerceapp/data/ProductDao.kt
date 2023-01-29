package com.ahmetozaydin.ecommerceapp.data

import androidx.annotation.RequiresPermission
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ahmetozaydin.ecommerceapp.data.Product
import retrofit2.http.GET

@Dao
interface ProductDao {
    @Insert
    suspend fun insertEntity(product: Product)
    @Query(value = "DELETE FROM product")
    suspend fun deleteAllRecords()
    @Query(value = "SELECT * FROM product")
    suspend fun getAllRecords(): List<Product>
    @Query(value = "SELECT * FROM product WHERE id = :id")
    suspend fun getRecord(id : Int) : Product
}