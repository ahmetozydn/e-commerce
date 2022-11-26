package com.ahmetozaydin.ecommerceapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmetozaydin.ecommerceapp.databinding.FragmentLogInBinding
import com.ahmetozaydin.ecommerceapp.view.MainActivity


class LogInFragment : Fragment() {
   private lateinit var binding : FragmentLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSignInAnonymously.setOnClickListener {
            val intent = Intent(activity,MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

}