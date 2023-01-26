package com.ahmetozaydin.ecommerceapp.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.ahmetozaydin.ecommerceapp.adapter.ImageAdapter
import com.ahmetozaydin.ecommerceapp.databinding.ActivityProductDetailsBinding
import com.ahmetozaydin.ecommerceapp.model.Product

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    private  var imageList= ArrayList<String>()
    private lateinit var imageAdapter: ImageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        binding.recyclerViewAllImages.layoutManager = layoutManager
        val products = intent.getParcelableExtra<Product>("product")
        Log.i(TAG, "onCreate: $products")
        binding.textViewProductDescription.text = products?.description
        ("Stock: "+products?.stock.toString()).also { binding.textViewProductFeatures.text = it }
        ("$" +products?.price.toString()).also { binding.textViewProductPrice.text = it }
        binding.textViewProductName.text = products?.title
        binding.ratingBar.rating = products?.rating!!.toString().toFloat()
        products.images?.forEach {
            imageList.add(it)
        }
        if(!imageList.contains(products.thumbnail)){
            imageList.add(products.thumbnail.toString())
        }
        println(imageList)
        println(imageList.size)
        imageAdapter = ImageAdapter(imageList,this)
        binding.recyclerViewAllImages.adapter = imageAdapter
        //val snapHelper :SnapHelper = PagerSnapHelper()
        PagerSnapHelper().attachToRecyclerView(binding.recyclerViewAllImages)

    }
}