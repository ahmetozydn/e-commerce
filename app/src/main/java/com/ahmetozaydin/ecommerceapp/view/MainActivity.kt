package com.ahmetozaydin.ecommerceapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ahmetozaydin.ecommerceapp.R
import com.ahmetozaydin.ecommerceapp.adapter.ProductsAdapter
import com.ahmetozaydin.ecommerceapp.databinding.ActivityMainBinding
import com.ahmetozaydin.ecommerceapp.fragment.FavoritesFragment
import com.ahmetozaydin.ecommerceapp.fragment.HomeFragment
import com.ahmetozaydin.ecommerceapp.fragment.ProfileFragment
import com.ahmetozaydin.ecommerceapp.fragment.ChartFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var homeFragment = HomeFragment()
    private var favoritesFragment = FavoritesFragment()
    private var chartFragment = ChartFragment()
    private var profileFragment = ProfileFragment()
    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        beginTransaction(homeFragment)

        binding.bottomNavigationView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.action_home -> {
                    beginTransaction(homeFragment)
                }
                R.id.action_favorites ->{
                    beginTransaction(favoritesFragment)
                }
                R.id.action_chart ->{
                    beginTransaction(chartFragment)
                }
                R.id.action_profile ->{
                    beginTransaction(profileFragment)
                }
                else -> true
            }
            return@setOnItemSelectedListener true
        }


    }
    private fun beginTransaction(fragment : Fragment){
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, fragment)
                commit()
            }
        }
    }
