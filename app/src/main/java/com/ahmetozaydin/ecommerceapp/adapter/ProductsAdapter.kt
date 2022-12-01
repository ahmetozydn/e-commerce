package com.ahmetozaydin.ecommerceapp.adapter

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.ecommerceapp.databinding.CardViewBinding
import com.ahmetozaydin.ecommerceapp.model.Product
import com.ahmetozaydin.ecommerceapp.view.ProductDetailsActivity
import com.squareup.picasso.Picasso
import java.io.InputStream
import java.net.URL


class ProductsAdapter(

    private val products: List<Product>,
    private var imageViews: ArrayList<Drawable>,
    val context: Context,
    val activity: Activity,
    private val listener: Listener
) : RecyclerView.Adapter<ProductsAdapter.PlaceHolder>() {
    private var bitmap: Bitmap? = null
    companion object{
         val checkBoxHashmap: HashMap<Int, Boolean> = HashMap<Int, Boolean>()
    }
    private lateinit var url: URL
    private var imageUrl: String? = null
    private var inputStream: InputStream? = null
    private lateinit var context1: Context

    interface Listener {
        fun onItemClick(products: Product)//service : Service de alabilir.
    }

    class PlaceHolder(val binding: CardViewBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceHolder {// layout ile bağlama işlemi, view binding ile
        val binding = CardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PlaceHolder,
        position: Int
    ) {

        Picasso.with(context).load(products[position].thumbnail).into(holder.binding.imageOfProduct)
        val thread = Thread {
            /*  imageUrl = products[position].thumbnail.toString()
             inputStream =  URL(imageUrl).openStream();
              bitmap = BitmapFactory.decodeStream(inputStream);*/
            println("Thread is worked")
            /* url = URL(products[position].thumbnail)// bunlar local değişken olmasa daha iyi olabilir
             image = BitmapFactory.decodeStream(
                 url.openConnection().getInputStream())*/
        }
        thread.start()

        //holder.binding.imageOfProduct.setImageBitmap(bitmap)
        /*Glide.with(products[position].thumbnail.context).load(context.response.get(position).getThumbnail()).into(holder.binding.imageOfProduct);
        holder.binding.imageOfProduct.setImageBitmap(null)
        Glide.with(context)
            .load(imageUrl)
            .centerCrop()
            .into(holder.binding.imageOfProduct);*/
        // Picasso.with(context).load(imageUrl).into(holder.binding.imageOfProduct);
        /* Picasso.with(context).load(imageUrl).fit().centerCrop()
             .placeholder(R.drawable.ic_home)
             .error(R.drawable.ic_favorite_filled)
             .into(holder.binding.imageOfProduct);*/
        activity.runOnUiThread {
            Log.i(TAG, "runOnUiThread is worked.")
            holder.binding.textViewProductName.text = products[position].title.toString()
            holder.binding.textViewProductPrice.text = "$" + products[position].price.toString()
            //holder.binding.imageOfProduct.setImageBitmap(image)
        }
        //activity.runO nUiThread
        holder.binding.buttonAddToShopping.setOnClickListener {
            //listener.onItemClick(products[position])
            println(holder.absoluteAdapterPosition)
        }
        holder.binding.cardView.setOnClickListener {

            println("clicked positon is " + products[position].id)
            println(holder.binding.imageOfProduct.id)
            println("clicked holder.binding.cardView.id is" + holder.binding.cardView.id)
            val intent = Intent(context, ProductDetailsActivity::class.java)
            //listener.onItemClick(products[position])
            intent.putExtra("product", products[position])
            context.startActivity(intent)
        }
        holder.binding.checkBox.setOnClickListener {
            if (holder.binding.checkBox.isChecked && !checkBoxHashmap.contains(holder.absoluteAdapterPosition)) {
                checkBoxHashmap.put(holder.absoluteAdapterPosition, true)
            }

            if (holder.binding.checkBox.isChecked)
                checkBoxHashmap.put(holder.absoluteAdapterPosition, true)
            else
                checkBoxHashmap.remove(holder.absoluteAdapterPosition)
            if(!checkBoxHashmap.size.equals(0))
                println(checkBoxHashmap)
        }
    }
    override fun getItemCount(): Int {
        return products.count()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onViewAttachedToWindow(holder: PlaceHolder) {
        super.onViewAttachedToWindow(holder)
        if(checkBoxHashmap.contains(holder.absoluteAdapterPosition)){
            holder.binding.checkBox.isChecked = true
        }
    }

    override fun onViewRecycled(holder: PlaceHolder) {
        holder.binding.checkBox.isChecked = false // - this line do the trick
        super.onViewRecycled(holder)
    }
}