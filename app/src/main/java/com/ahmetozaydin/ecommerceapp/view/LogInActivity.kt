package com.ahmetozaydin.ecommerceapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahmetozaydin.ecommerceapp.R
import com.ahmetozaydin.ecommerceapp.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {
    lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}