package com.ahmetozaydin.ecommerceapp.data

import android.database.Cursor
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery


@Dao
interface FavoriteDao {
    @Insert
    suspend fun insertEntity(favorite: Favorite)
    @Insert
     fun insertAll(vararg favorite: Favorite): List<Long>
    @RawQuery
    fun getEntities(query : SupportSQLiteQuery) : List<Long>
    @Query("SELECT * FROM favorite")
   /* var liveUsers: LiveData<MutableList<User?>?>? =
        rawDao.getUsers(SimpleSQLiteQuery("SELECT * FROM User ORDER BY name DESC"))*/
    fun getAllEntities() : List<Favorite>

    @Query("SELECT * FROM favorite WHERE id = :id")
    fun getEntity(id : Int) : Favorite

    @Query("DELETE FROM favorite WHERE id =  :id")
    suspend fun delete(id: Int) : Int
    @Query("SELECT id FROM favorite WHERE id =  :id")
    fun searchForEntity(id: Int): Int
    @Query("UPDATE favorite SET isChecked = 0 WHERE id = :id")
    fun updateEntity(id:Int)
    @Query("SELECT COUNT(*) FROM favorite")
    fun rowCount() : Int
    @Query("SELECT isChecked FROM favorite WHERE id = :id")
    fun isAddedToFavorite(id : Int) : Boolean
    @Query("SELECT * FROM favorite")
    fun getCursorAll(): Cursor?
}