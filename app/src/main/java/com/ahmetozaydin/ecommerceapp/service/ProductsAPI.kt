package com.ahmetozaydin.ecommerceapp.service

import com.ahmetozaydin.ecommerceapp.model.BaseClass
import com.ahmetozaydin.ecommerceapp.model.Product
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ProductsAPI {
    @GET("products?limit=100") //extension
    fun getData() : Call<BaseClass>
    @GET
    fun getCategorizedProduct(@Url text : String) : Call<BaseClass>//sınıf adı farklı Single olması gerekirdi
    @GET("categories")
    fun getCategoryFromAPI() : Single<List<String>>
    @GET("smartphones")
    fun getSmartPhonesFromAPI() : Single<BaseClass>
}