package com.ahmetozaydin.ecommerceapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class Image(
    @ColumnInfo(name = "product_id")
    val productId: Int? = null,
    @ColumnInfo(name = "url")
    val url: String? = null,
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
)
