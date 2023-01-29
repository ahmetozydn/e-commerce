package com.ahmetozaydin.ecommerceapp.adapter


import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.ecommerceapp.R
import com.ahmetozaydin.ecommerceapp.data.Cart
import com.ahmetozaydin.ecommerceapp.data.CartDatabase
import com.ahmetozaydin.ecommerceapp.data.ImageDatabase
import com.ahmetozaydin.ecommerceapp.data.ProductDatabase
import com.ahmetozaydin.ecommerceapp.databinding.EachCartBinding
import com.ahmetozaydin.ecommerceapp.model.Product
import com.ahmetozaydin.ecommerceapp.view.ProductDetailsActivity
import com.ahmetozaydin.ecommerceapp.viewmodel.CartViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartAdapter(
    private val cartList: ArrayList<Cart>,
    val context: Context,
    val database: CartDatabase
) : RecyclerView.Adapter<CartAdapter.PlaceHolder>() {

    interface Listener {
        fun onItemClick(products: Product)//service : Service de alabilir.
    }
    class PlaceHolder(val binding: EachCartBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val binding = EachCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val binding =
            DataBindingUtil.inflate<EachCartBinding>(inflater, R.layout.each_cart, parent, false)
        return PlaceHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PlaceHolder,
        position: Int
    ) {

        holder.binding.product = cartList[position]
        /*with(holder){
            with(cartList[position]){
                binding.imageOfProduct.downloadFromUrl(this.thumbnail, placeholderProgressBar(holder.itemView.context))
                //binding.textViewProductName.text = this.title
                (dollar + this.price.toString()).also { binding.textViewProductPrice.text = it }
            }
        }*/

        holder.binding.productQuantityMinus.setOnClickListener {
            println("minus")
            changeProductQuantity(false, holder)
        }
       holder.itemView.setOnClickListener {
           val id = cartList[position].id
           CoroutineScope(Dispatchers.IO).launch() {
               val images = ImageDatabase(context = context).imageDao().getRecord(id!!)
               val item = ProductDatabase(context).productDao().getRecord(id)
               val product1 = Product(item.id,item.title,item.description,item.price,item.discountPercentage,item.rating,item.stock,item.brand,item.category,item.thumbnail,images)
               Log.i(TAG, "onViewClicked: the value of product is : $product1")
               val intent = Intent(context, ProductDetailsActivity::class.java)
               intent.putExtra("product", product1)
               context.startActivity(intent)
           }

            Log.i(TAG, "onBindViewHolder: ${cartList[position].id}")
            val viewModel = CartViewModel()
             viewModel.onViewClicked(context, cartList[position].id!!) // TODO viewmodel isn't triggered
        }
        holder.binding.productQuantityPlus.setOnClickListener {
            println("plus")
            changeProductQuantity(true, holder)
        }
        /*holder.binding.imageOfProduct.downloadFromUrl(cartList.get(position).thumbnail, placeholderProgressBar(holder.itemView.context))
        holder.binding.textViewProductName.text = cartList.get(position).title
        (dollar + cartList.get(position).price.toString()).also { holder.binding.textViewProductPrice.text = it } //dollar olarak kendi değişkenimi kullanmadım.*/
    }

    override fun getItemCount(): Int {
        println("SIZE " + cartList.size)
        return cartList.size ?: 0
    }

    fun deleteItem(position: Int) {
        cartList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, cartList.size)
    }

    fun getItemInfo(position: Int): Int? {
        return cartList[position].id
    }

    private fun changeProductQuantity(increaseQuantity: Boolean, holder: PlaceHolder) {
        val viewModel = CartViewModel()
            holder.binding.product?.id?.let { viewModel.updateQuantity(increaseQuantity,it,database,holder) }
            /*val database = CartDatabase.invoke(context)
            var quantity = holder.binding.product?.id!!.let { database.cartDao().getQuantity(it) }
            println("the amount of quantity: $quantity")
            if (increaseQuantity) {
                database.cartDao().updateQuantity(holder.binding.product?.id!!, ++quantity)
                viewModel.calculateTotalAmounth(database)
                println("new quantity : " + holder.binding.product?.id!!.let {
                    database.cartDao().getQuantity(it)
                })
                viewModel.calculateTotalAmounth(database)
            } else if (!increaseQuantity && quantity > 1) {
                database.cartDao().updateQuantity(holder.binding.product?.id!!, --quantity)
                viewModel.calculateTotalAmounth(database)
                println("new quantity : " + holder.binding.product?.id!!.let {
                    database.cartDao().getQuantity(it)
                })
            }
            val price = database.cartDao().getPrice(holder.binding.product?.id!!)
            CoroutineScope(Dispatchers.Main).launch {
                holder.binding.productQuantityEditText.setText(quantity.toString())
                println("the edit text set to $quantity")
                //holder.binding.textViewProductPrice.text = ""
                holder.binding.textViewProductPrice.text = (quantity * price).toString()
            }*/

    }

    fun onQuantityTextChanged(text: CharSequence, cart: Cart, priceTextView: TextView) {
        val quantity = text.toString()
        if (quantity.isNotEmpty()) {
            val quantityNumber = quantity.toDouble().toInt()
        }
    }
}