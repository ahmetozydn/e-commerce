package com.ahmetozaydin.ecommerceapp.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.ahmetozaydin.ecommerceapp.R
import com.ahmetozaydin.ecommerceapp.data.Favorite
import com.ahmetozaydin.ecommerceapp.databinding.EachCartBinding
import com.ahmetozaydin.ecommerceapp.databinding.EachFavoriteBinding
import com.ahmetozaydin.ecommerceapp.model.Product
import com.ahmetozaydin.ecommerceapp.utils.downloadFromUrl
import com.ahmetozaydin.ecommerceapp.view.ProductDetailsActivity
import kotlin.math.roundToInt

class FavoriteAdapter(
    private val favoriteList: ArrayList<Favorite>,
    val context: Context
) : RecyclerView.Adapter<FavoriteAdapter.PlaceHolder>() {
    interface Listener {
        fun onItemClick(products: Product)//service : Service de alabilir.
    }
    class PlaceHolder(val binding: EachFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceHolder {
        val binding = DataBindingUtil.inflate<EachFavoriteBinding>(LayoutInflater.from(parent.context),
                R.layout.each_favorite, parent, false)
        return PlaceHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PlaceHolder,
        position: Int
    ) {
        holder.binding.imageOfProduct.downloadFromUrl(favoriteList[position].thumbnail,
            CircularProgressDrawable(context)
        )
        holder.binding.eachFavorite = favoriteList[position]
        holder.itemView.setOnClickListener {
            //val item = favoriteList[position]
            //val product=Product(item.id,item.title,item.description,item.price,item.discountPercentage,item.rating,item.stock,item.brand,item.category,item.thumbnail,ArrayList())
            //val intent = Intent(context, ProductDetailsActivity::class.java)
            //intent.putExtra("product", product)
            //context.startActivity(intent)
        }
        //val double = favoriteList[position].rating
        //val dolarSign = "$"
        //val doubleWithLastTwoDigits = ((double?.times(10.0))?.roundToInt() ?: 0) / 10.0
        //holder.binding.textViewProductName.text = favoriteList[position].title
        //holder.binding.textViewProductDescription.text = favoriteList[position].description
        //(dolarSign + favoriteList[position].price.toString()).also { holder.binding.textViewProductPrice.text = it }
       // holder.binding.textViewRatingCount.text = doubleWithLastTwoDigits.toString()
}
    override fun getItemCount(): Int {
        println("getItemCount"
                +favoriteList.size)
        return favoriteList.size
    }
    fun deleteItem(position: Int) {
        favoriteList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, favoriteList.size)
    }
    fun getItemInfo(position: Int): Int? {
        return favoriteList[position].id
    }

}