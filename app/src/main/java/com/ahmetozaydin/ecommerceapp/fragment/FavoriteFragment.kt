package com.ahmetozaydin.ecommerceapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.ecommerceapp.adapter.FavoriteAdapter
import com.ahmetozaydin.ecommerceapp.data.Favorite
import com.ahmetozaydin.ecommerceapp.data.FavoriteDatabase
import com.ahmetozaydin.ecommerceapp.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private var favoriteAdapter: FavoriteAdapter? = null
    private   var favoriteList = ArrayList<Favorite>()
    private var favoriteDatabase : FavoriteDatabase? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        binding.favoriteRecyclerView.layoutManager = layoutManager
        favoriteDatabase = context?.let { FavoriteDatabase.invoke(it) }
        GlobalScope.launch {
            println("GlobalScope is worked")
            favoriteDatabase?.favoriteDao()?.getAllEntities()!!.forEach{
                favoriteList.add(it)
            }
            if(favoriteList.isEmpty()){
                println("is empty? "+favoriteList.size)
                binding.relativeLayoutFavorite.visibility = View.VISIBLE
                binding.favoriteRecyclerView.visibility = View.INVISIBLE
            }else{
                binding.relativeLayoutFavorite.visibility = View.INVISIBLE// TODO(Can't create handler inside thread)
                binding.favoriteRecyclerView.visibility = View.VISIBLE
            }
        }
        binding.progressBar.visibility = View.GONE
        favoriteAdapter = FavoriteAdapter(favoriteList,requireContext())
        binding.favoriteRecyclerView.adapter = favoriteAdapter


    }

}