package com.ahmetozaydin.ecommerceapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ahmetozaydin.ecommerceapp.model.BaseClass
import com.ahmetozaydin.ecommerceapp.model.Product
import com.ahmetozaydin.ecommerceapp.service.ProductsAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CategoryViewModel(application: Application) : BaseViewModel(application) {
    private val disposable = CompositeDisposable() //Low memory usages.
    var categoryList = MutableLiveData<ArrayList<String>>(arrayListOf())
    private val url = "https://dummyjson.com/products/"
    private val categoryBaseUrl = "https://dummyjson.com/products/category/"
    var categorizedProducts = MutableLiveData<ArrayList<Product>>()
    private val api = Retrofit
        .Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ProductsAPI::class.java)

    fun getCategoryFromAPI(): ArrayList<String>? {
        runBlocking {
            launch {
                disposable.add(
                    api.getCategoryFromAPI()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<String>>() {
                            override fun onSuccess(value: List<String>?) {
                                categoryList.value = value as ArrayList<String>?
                            }

                            override fun onError(e: Throwable?) {
                                e?.printStackTrace()
                            }
                        })
                )
            }
        }
        return categoryList.value
    }

    fun getCategorizedProductFromAPI(string: String) {
        //supportActionBar?.title = categoryType
        println(categoryBaseUrl + string)
        val retrofit = Retrofit.Builder()
            .baseUrl(categoryBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductsAPI::class.java)
        val call = service.getCategorizedProduct(categoryBaseUrl + string)
        call.enqueue(object : Callback<BaseClass> {
            override fun onFailure(call: Call<BaseClass>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<BaseClass>, response: Response<BaseClass>) {
                println(response.body())
                response.body().let {
                    categorizedProducts.value = it?.products as ArrayList<Product>?
                }
            }
        })
    }
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}