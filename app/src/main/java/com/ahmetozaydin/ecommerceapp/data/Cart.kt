package com.ahmetozaydin.ecommerceapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "cart")
data class Cart(
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @SerializedName("title")
    @ColumnInfo(name = "title")
    val title: String? = null,
    @SerializedName("discountPercentage")
    @ColumnInfo(name = "discountPercentage")
    val discountPercentage: Double? = null,
    @SerializedName("description")
    @ColumnInfo(name = "description")
    val description: String? = null,
    @SerializedName("price")
    @ColumnInfo(name = "price")
    val price: Int? = null,
    @SerializedName("rating")
    @ColumnInfo(name = "rating")
    val rating: Double? = null,
    @SerializedName("stock")
    @ColumnInfo(name = "stock")
    val stock : Int?,
    @SerializedName("brand")
    @ColumnInfo(name = "brand")
    val brand : String?,
    @SerializedName("thumbnail")
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String? = null,
    @SerializedName("isAddedToCart")
    @ColumnInfo(name = "isAddedToCart")
    val isAddedToCart: Boolean,
    @ColumnInfo(name = "quantity")
    val quantity : Int,
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
)