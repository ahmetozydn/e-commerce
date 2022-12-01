package com.ahmetozaydin.ecommerceapp.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ahmetozaydin.ecommerceapp.databinding.ActivityProductDetailsBinding
import com.ahmetozaydin.ecommerceapp.model.Product
import com.squareup.picasso.Picasso
import java.net.URL

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    private  var image: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val products = intent.getParcelableExtra<Product>("product")
        binding.textViewProductDescription.text = products?.description
        binding.textViewProductFeatures.text =  "Stock: "+products?.stock.toString()
        binding.textViewProductPrice.text = "$" +products?.price.toString()
        binding.textViewProductName.text = products?.title
        binding.ratingBar.rating = products?.rating!!.toString().toFloat()
        Picasso.with(this).load(products.thumbnail).into(binding.imageOfProduct)
        val thread: Thread = Thread {
                val url = URL(products.thumbnail)

            //image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        }
        thread.start()
        /*this.runOnUiThread {
            binding.imageOfProduct.setImageBitmap(image)
        }*/
    }
}