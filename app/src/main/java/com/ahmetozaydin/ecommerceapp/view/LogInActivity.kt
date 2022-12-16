package com.ahmetozaydin.ecommerceapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahmetozaydin.ecommerceapp.R
import com.ahmetozaydin.ecommerceapp.adapter.TabLayoutAdapter
import com.ahmetozaydin.ecommerceapp.databinding.ActivityLogInBinding
import com.ahmetozaydin.ecommerceapp.fragment.LogInFragment
import com.ahmetozaydin.ecommerceapp.fragment.SignUpFragment

class LogInActivity : AppCompatActivity() {
    lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Ecommerceapp_NoActionBar)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpTabs()
    }

    private fun setUpTabs() {
        val adapter = TabLayoutAdapter(supportFragmentManager)
        adapter.addFragment(LogInFragment(),"Log In")
        adapter.addFragment(SignUpFragment(),"Sign Up")
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        /*  tabLayout.getTabAt(0)?.setIcon(R.drawable.vector_card)
          tabLayout.getTabAt(1)?.setIcon(R.drawable.vector_card)
  */
    }

}