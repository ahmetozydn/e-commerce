package com.ahmetozaydin.ecommerceapp.data

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface CartDao {
    @Insert
    suspend fun insertEntity(cart: Cart)
    @Insert
    fun insertAll(vararg cart: Cart): List<Long>
    @RawQuery
    fun getEntities(query : SupportSQLiteQuery) : List<Long>
    @Query("SELECT * FROM cart")
            /* var liveUsers: LiveData<MutableList<User?>?>? =
                 rawDao.getUsers(SimpleSQLiteQuery("SELECT * FROM User ORDER BY name DESC"))*/
    fun getAllEntities() : List<Cart>
    @Query("DELETE FROM cart WHERE id =  :id")
    suspend fun delete(id: Int) : Int
    @Query("SELECT id FROM cart WHERE id =  :id")
    fun searchForEntity(id: Int): Int
    @Query("UPDATE cart SET isAddedToCart = 0 WHERE id = :id")
    fun updateEntity(id:Int)
    @Query("SELECT COUNT(*) FROM cart")
    fun rowCount() : Int

    @Query("SELECT * FROM cart")
    fun getCursorAll(): Cursor?
}