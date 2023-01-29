package com.ahmetozaydin.ecommerceapp.view

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ahmetozaydin.ecommerceapp.R
import com.ahmetozaydin.ecommerceapp.databinding.ActivityMainBinding
import com.ahmetozaydin.ecommerceapp.fragment.*
import com.ahmetozaydin.ecommerceapp.utils.network.ConnectivityObserver
import com.ahmetozaydin.ecommerceapp.utils.network.NetworkConnectivityObserver
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var homeFragment = HomeFragment()
    private lateinit var connectivityObserver: ConnectivityObserver

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage("No Internet Connection")
        builder.setTitle("Attention!")
        builder.setCancelable(false)
        val alertDialog = builder.create()
        val x = Snackbar.make(
            binding.frameLayout,
            R.string.no_internet_connection,
            Snackbar.LENGTH_INDEFINITE
        )
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        connectivityObserver.observe().onEach {
            if (it == ConnectivityObserver.Status.Available) {
                alertDialog.dismiss()
                Log.i(TAG, "onCreate: status in $it")
                //Snackbar.make(binding.frameLayout, "the connection is available", Snackbar.LENGTH_SHORT) .show()
            } else if (it == ConnectivityObserver.Status.Lost) {
                Log.i(TAG, "onCreate: status in $it")
                alertDialog.show()
            }

        }.launchIn(lifecycleScope)

        beginTransaction(homeFragment)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    beginTransaction(homeFragment)
                }
                R.id.action_category -> {
                    beginTransaction(CategoryFragment())
                }
                R.id.action_favorites -> {
                    //val favoritesFragment = FavoriteFragment()//TODO(implement a better structure)
                    beginTransaction(FavoriteFragment())
                }
                R.id.action_cart -> {
                    beginTransaction(CartFragment())
                }
                R.id.action_profile -> {
                    beginTransaction(ProfileFragment())
                }
                else -> return@setOnItemSelectedListener true
            }
            return@setOnItemSelectedListener true
        }

    }


    private fun beginTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }

}
