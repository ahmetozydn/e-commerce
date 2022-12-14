package com.ahmetozaydin.ecommerceapp.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.ahmetozaydin.ecommerceapp.data.Favorite
import com.ahmetozaydin.ecommerceapp.databinding.EachFavoriteBinding
import com.ahmetozaydin.ecommerceapp.utils.downloadFromUrl
import kotlin.math.roundToInt

class FavoriteAdapter(
    private val favoriteList: ArrayList<Favorite>,
    val context: Context
) : RecyclerView.Adapter<FavoriteAdapter.PlaceHolder>() {

    class PlaceHolder(val binding: EachFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceHolder {
        val binding =
            EachFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PlaceHolder,
        position: Int
    ) {
        holder.binding.imageOfProduct.downloadFromUrl(favoriteList[position].thumbnail,
            CircularProgressDrawable(context)
        )
        val double = favoriteList[position].rating
        val dolarSign = "$"
        val doubleWithLastTwoDigits = ((double?.times(10.0))?.roundToInt() ?: 0) / 10.0
        holder.binding.textViewProductName.text = favoriteList[position].title
        holder.binding.textViewProductDescription.text = favoriteList[position].description
        (dolarSign + favoriteList[position].price.toString()).also { holder.binding.textViewProductPrice.text = it }
        holder.binding.textViewRatingCount.text = doubleWithLastTwoDigits.toString()
}
    override fun getItemCount(): Int {
        println("getItemCount"
                +favoriteList.size)
        return favoriteList.size
    }
}