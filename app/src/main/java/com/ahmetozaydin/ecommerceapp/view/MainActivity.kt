package com.ahmetozaydin.ecommerceapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ahmetozaydin.ecommerceapp.R
import com.ahmetozaydin.ecommerceapp.databinding.ActivityMainBinding
import com.ahmetozaydin.ecommerceapp.fragment.CartFragment
import com.ahmetozaydin.ecommerceapp.fragment.FavoriteFragment
import com.ahmetozaydin.ecommerceapp.fragment.HomeFragment
import com.ahmetozaydin.ecommerceapp.fragment.ProfileFragment
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var homeFragment = HomeFragment()
    private val favoritesFragment = FavoriteFragment()
    private var cartFragment = CartFragment()
    private var profileFragment = ProfileFragment()
    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        beginTransaction(HomeFragment())
        binding.bottomNavigationView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.action_home -> {
                    beginTransaction(HomeFragment())
                }
                R.id.action_favorites ->{
                    //val favoritesFragment = FavoriteFragment()//TODO(implement a better structure)
                    beginTransaction(FavoriteFragment())
                }
                R.id.action_cart ->{
                    beginTransaction(CartFragment())
                }
                R.id.action_profile ->{
                    beginTransaction(ProfileFragment())
                }
                else ->  return@setOnItemSelectedListener true
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
