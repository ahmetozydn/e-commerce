package com.ahmetozaydin.ecommerceapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ahmetozaydin.ecommerceapp.R
import com.ahmetozaydin.ecommerceapp.databinding.ActivityMainBinding
import com.ahmetozaydin.ecommerceapp.fragment.FavoritesFragment
import com.ahmetozaydin.ecommerceapp.fragment.HomeFragment
import com.ahmetozaydin.ecommerceapp.fragment.ProfileFragment
import com.ahmetozaydin.ecommerceapp.fragment.ShoppingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object {
        const val BASE_URL = "https://productproject.herokuapp.com/"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val home = HomeFragment()
        beginTransaction(home)
        binding.bottomNavigationView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.action_home -> {
                    val fragment = HomeFragment()
                    beginTransaction(fragment)
                }
                R.id.action_favorites ->{
                    val fragment = FavoritesFragment()
                    beginTransaction(fragment)
                }
                R.id.action_shopping ->{
                    val fragment = ShoppingFragment()
                    beginTransaction(fragment)
                }
                R.id.action_profile ->{
                    val fragment = ProfileFragment()
                    beginTransaction(fragment)
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
