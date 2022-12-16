package com.ahmetozaydin.ecommerceapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.ecommerceapp.R
import com.ahmetozaydin.ecommerceapp.databinding.EachCategoryBinding

class CategoriesAdapter(private val categories : ArrayList<String>, val context : Context, private val listener: Listener) : RecyclerView.Adapter<CategoriesAdapter.PlaceHolder>() {
    private var previousPosition: Int = -1
    class PlaceHolder(val binding: EachCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
    }
    interface Listener {
        fun onItemClick(position: Int,
                        holder: PlaceHolder,
                        )//service : Service de alabilir.
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val binding = EachCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PlaceHolder(binding)
    }
    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        holder.binding.buttonEachCategory.text = categories[position]
        val color = ContextCompat.getColor(context, R.color.light_orange)
        val colorWhite = ContextCompat.getColor(context, R.color.white)
        holder.binding.buttonEachCategory.setOnClickListener{
            previousPosition = holder.absoluteAdapterPosition
            holder.itemId

            if(previousPosition == holder.absoluteAdapterPosition){
                it.setBackgroundColor(color)
            }else{
                it.setBackgroundColor(colorWhite)
            }
            /*if (colorId != color) {
                println("if is worked")
                holder.itemView.setBackgroundColor(color)
            } else {
                holder.itemView.setBackgroundColor(colorWhite)
            }*/
            listener.onItemClick(position,holder)
        }
    }
    override fun getItemCount(): Int {
        return categories.size
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }
}