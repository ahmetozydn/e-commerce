package com.ahmetozaydin.ecommerceapp.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.ecommerceapp.databinding.CardViewBinding
import com.ahmetozaydin.ecommerceapp.model.Product
import com.squareup.picasso.Picasso

class CategorizedProduct(
    private val products: ArrayList<Product>,
    val context: Context
) : RecyclerView.Adapter<CategorizedProduct.PlaceHolder>() {
    private  lateinit var image : Bitmap
    interface Listener {
        fun categoryButtonClicked(
            products: ArrayList<Product>,
            holder: PlaceHolder,
            position: Int
        )//service : Service de alabilir.
    }
    class PlaceHolder(val binding: CardViewBinding) : RecyclerView.ViewHolder(binding.root) {
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceHolder {// layout ile bağlama işlemi, view binding ile
        val binding =
            CardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceHolder(binding)
    }
    override fun onBindViewHolder(
        holder: PlaceHolder,
        position: Int
    ) {//bağlamadan sonraki kısım,hangi item ne verisi gösterecek.
       /* val thread = Thread {
            println("The thread is worked")

        }
        thread.start()
        holder.binding.imageOfProduct.setImageBitmap(image)
       */
        //products[position].category.toString()
        /*if(categories.contains(it.category)){
            categories.add(it.category!!)
            holder.binding.buttonEachCategory.text = it.category
            println(it.category)
            println("hello world" +
                    "")
        }*/
        Picasso.with(context).load(products[position].thumbnail).into(holder.binding.imageOfProduct)
        holder.binding.textViewProductName.text =  products[position].title
        holder.binding.textViewProductPrice.text =  "$"+products[position].price.toString()
    }
    override fun getItemCount(): Int {
        return products.size
    }
    /*override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }*/

}