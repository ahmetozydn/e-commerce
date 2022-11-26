package com.ahmetozaydin.ecommerceapp.model

import com.google.gson.annotations.SerializedName

data class Products(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("image") var image: ArrayList<String>? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("category") var category: String? = null,
    @SerializedName("memory") var memory: Int? = null,
    @SerializedName("color") var color: String? = null,
    @SerializedName("price") var price: Int? = null
)
