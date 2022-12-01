package com.ahmetozaydin.ecommerceapp.service

import com.ahmetozaydin.ecommerceapp.model.BaseClass
import com.ahmetozaydin.ecommerceapp.model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ProductsAPI {
    @GET("products?limit=100")
    fun getData() : Call<BaseClass>
    @GET
    fun fetchData(@Url text : String) : Call<BaseClass>
}