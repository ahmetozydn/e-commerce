package com.ahmetozaydin.ecommerceapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BaseClass(
    @SerializedName("products") val products: List<Product>? = null,
    @SerializedName("total") val total: Int? = null,
    @SerializedName("skip") val skip: Int? = null,
    @SerializedName("limit") val limit: Int? = null
) : Parcelable

@Parcelize
data class Product(
    @SerializedName("id") val id: Int? = null,//named as productId, productName... and productList --> products
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("price") val price: Int? = null,
    @SerializedName("discountPercentage") val discountPercentage: Double? = null,
    @SerializedName("rating") val rating: Double? = null,
    @SerializedName("stock") val stock: Int? = null,
    @SerializedName("brand") val brand: String? = null,
    @SerializedName("category") val category: String? = null,
    @SerializedName("thumbnail") val thumbnail: String? = null,
    @SerializedName("images") val images: List<String>? = null
) : Parcelable