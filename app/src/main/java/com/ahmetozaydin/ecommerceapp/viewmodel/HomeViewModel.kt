package com.ahmetozaydin.ecommerceapp.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmetozaydin.ecommerceapp.data.Image
import com.ahmetozaydin.ecommerceapp.data.ImageDatabase
import com.ahmetozaydin.ecommerceapp.data.ProductDatabase
import com.ahmetozaydin.ecommerceapp.model.BaseClass
import com.ahmetozaydin.ecommerceapp.model.Product
import com.ahmetozaydin.ecommerceapp.service.ProductsAPI
import com.ahmetozaydin.ecommerceapp.utils.CustomSharedPreferences
import com.ahmetozaydin.ecommerceapp.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel : ViewModel() {
    private val _products = MutableLiveData<ArrayList<Product>?>()
    val products get() = _products
    private var customPreferences = CustomSharedPreferences()
    private var refreshTime = 0.01 * 60 * 1000 * 1000 * 1000L

    fun getData(context: Context) {
        val updateTime = customPreferences.getTime()
        //Log.i(TAG, "$refreshTime  getData: "+(System.nanoTime() - updateTime!!))
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite(context)
            Toast.makeText(context,"Products From SQLite",Toast.LENGTH_LONG).show()
        }else{
            getDataFromAPI(context)
            Toast.makeText(context,"Products From API",Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromAPI(context: Context){
        val retrofit = Retrofit
            .Builder()
            .baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductsAPI::class.java)
        val call = service.getData()
        call.enqueue(object : Callback<BaseClass> {
            override fun onFailure(call: Call<BaseClass>, t: Throwable) {
                t.printStackTrace()
            }
            override fun onResponse(
                call: Call<BaseClass>,
                response: Response<BaseClass>
            ) {
                response.body()?.let {
                    println("onResponse is worked")
                    it.products?.let { it1 -> storeInSQLite(context, it1 as ArrayList<Product>) }
                    _products.postValue(it.products as ArrayList<Product>?)
                }
            }
        })
    }

    fun storeInSQLite(context: Context, products: ArrayList<Product>) {
        println("storeInSQLLite")
        viewModelScope.launch {
            val productDb = ProductDatabase.invoke(context).productDao()
            val imageDb = ImageDatabase.invoke(context).imageDao()
            productDb.deleteAllRecords()
            imageDb.deleteAllRecords()
            products.forEach {
                val aProduct = com.ahmetozaydin.ecommerceapp.data.Product(it.id,it.title,it.description,it.price,it.discountPercentage,it.rating,it.stock,it.brand,it.category,
                it.thumbnail)
                productDb.insertEntity(aProduct)
            }
            products.forEach {
                it.images?.forEach { imageUrl ->
                    val anImage = Image(it.id, imageUrl)
                    imageDb.insert(anImage)
                }
            }
        }
        customPreferences.saveTime(System.nanoTime())
    }
    private fun getDataFromSQLite(context: Context) {
        viewModelScope.launch {
            val productDb = ProductDatabase(context).productDao().getAllRecords()
            val imageDb = ImageDatabase(context).imageDao().getAllRecords()
            val products1 = arrayListOf<Product>()
            productDb.forEach{
                val imageList = it.id?.let { it1 ->
                    ImageDatabase.invoke(context).imageDao().getRecord(it1)
                }
                val list = Product(it.id,it.title,it.description,it.price,it.discountPercentage,it.rating,it.stock,it.brand,it.category,
                    it.thumbnail,imageList)
                products1.add(list)
            }
            Toast.makeText(context,"Products From SQLite",Toast.LENGTH_LONG).show()
            _products.postValue(products1)
        }
    }
}