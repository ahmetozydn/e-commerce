package com.ahmetozaydin.ecommerceapp.view

import android.R
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmetozaydin.ecommerceapp.adapter.CategorizedProduct
import com.ahmetozaydin.ecommerceapp.databinding.ActivityCategoryBinding
import com.ahmetozaydin.ecommerceapp.model.BaseClass
import com.ahmetozaydin.ecommerceapp.model.Product
import com.ahmetozaydin.ecommerceapp.service.ProductsAPI
import kotlinx.coroutines.GlobalScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var categorizedProduct: CategorizedProduct
    private var productList = ArrayList<Product>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // a thread is removed from the code
        val layoutManager = GridLayoutManager(this, 1)
        binding.recyclerViewCategorizedProduct.layoutManager = layoutManager
        val categoryType = intent.getStringExtra("category_type")
        supportActionBar?.title = categoryType
        val url = "products/category/$categoryType"
        println(url)
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductsAPI::class.java)
        val call = service.getCategorizedProduct(url)
        call.enqueue(object : Callback<BaseClass> {
            override fun onFailure(call: Call<BaseClass>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<BaseClass>, response: Response<BaseClass>) {
                println(response.body())
                response.body().let {
                    it?.products?.forEach {
                        productList.add(it)
                    }
                    categorizedProduct = CategorizedProduct(productList, this@CategoryActivity)
                    binding.recyclerViewCategorizedProduct.adapter = categorizedProduct
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }
}



