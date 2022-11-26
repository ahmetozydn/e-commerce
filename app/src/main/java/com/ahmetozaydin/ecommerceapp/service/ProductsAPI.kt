package com.ahmetozaydin.ecommerceapp.service

import com.ahmetozaydin.ecommerceapp.model.Products
import retrofit2.Call
import retrofit2.http.GET

interface ProductsAPI {
    @GET("product")
    fun getData() : Call<ArrayList<Products>>
}