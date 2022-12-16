package com.ahmetozaydin.ecommerceapp.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.ecommerceapp.data.Cart
import com.ahmetozaydin.ecommerceapp.data.CartDatabase
import com.ahmetozaydin.ecommerceapp.databinding.EachCartBinding
import com.ahmetozaydin.ecommerceapp.utils.downloadFromUrl
import com.ahmetozaydin.ecommerceapp.utils.placeholderProgressBar
import kotlin.text.Typography.dollar

class CartAdapter(
    private val cartList: ArrayList<Cart>,
    val context: Context,
    val cartDatabase: CartDatabase?,private val strings: List<String>
) : RecyclerView.Adapter<CartAdapter.PlaceHolder>() {
    class PlaceHolder(val binding: EachCartBinding) : RecyclerView.ViewHolder(binding.root) {
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceHolder {
        val binding =
            EachCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PlaceHolder,
        position: Int
    ) {
        holder.binding.imageOfProduct.downloadFromUrl(cartList.get(position).thumbnail, placeholderProgressBar(holder.itemView.context))
        val dollarSign = "$"
        holder.binding.textViewProductName.text = cartList.get(position).title
        (dollar + cartList.get(position).price.toString()).also { holder.binding.textViewProductPrice.text = it } //dollar olarak kendi değişkenimi kullanmadım.
    }
    override fun getItemCount(): Int {
        println("SIZE "+cartList.size)
        return cartList.size ?: 0
    }
    fun updateCountryList(newCountryList: List<Cart>) {
        cartList.clear()
        cartList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}