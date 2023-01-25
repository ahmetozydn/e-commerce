package com.ahmetozaydin.ecommerceapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.ecommerceapp.R
import com.ahmetozaydin.ecommerceapp.databinding.EachCategoryBinding
import kotlin.collections.ArrayList

class CategoriesAdapter(
    private val categories: ArrayList<String>,
    val context: Context,
    private val listener: Listener
) : RecyclerView.Adapter<CategoriesAdapter.PlaceHolder>() {
    private var selectedItemPosition: Int = 0

    class PlaceHolder(val binding: EachCategoryBinding) : RecyclerView.ViewHolder(binding.root) {}
    interface Listener {
        fun onItemClick(
            position: Int,
            holder: PlaceHolder,
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val binding =
            EachCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    //TODO(is there a better way to change the color of only selected item? Saved in stackoverflow)
    override fun onBindViewHolder(
        holder: PlaceHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val item = holder.binding
        with(holder) {
            with(categories[position]) {
                binding.buttonEachCategory.text = this
            }
        }
        holder.binding.buttonEachCategory.setOnClickListener {
            // holder.binding.buttonEachCategory.setBackgroundColor(coloredBackground)
            selectedItemPosition = position
            listener.onItemClick(position, holder)
            notifyDataSetChanged()
        }
        if (selectedItemPosition == position) {
            item.buttonEachCategory.setBackgroundColor(Color.parseColor("#ff8f00"))
            item.buttonEachCategory.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            item.buttonEachCategory.setBackgroundColor(Color.parseColor("#ffffff"))
            item.buttonEachCategory.setTextColor(ContextCompat.getColor(context, R.color.black))
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
