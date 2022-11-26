package com.ahmetozaydin.ecommerceapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmetozaydin.ecommerceapp.databinding.FragmentHomeBinding
import com.ahmetozaydin.ecommerceapp.model.Products
import com.ahmetozaydin.ecommerceapp.service.ProductsAPI
import com.ahmetozaydin.ecommerceapp.view.MainActivity.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {
    private var isDone : Boolean = false
    private lateinit var binding: FragmentHomeBinding
   private var products = ArrayList<Products>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btn.setOnClickListener {
            fetchData()
        }
    }
    private fun fetchData(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductsAPI::class.java)
        val call = service.getData()
        call.enqueue(object : Callback<ArrayList<Products>> {
            override fun onFailure(call: Call<ArrayList<Products>>, t: Throwable) {
                println("error")
            }
            override fun onResponse(
                call: Call<ArrayList<Products>>,
                response: Response<ArrayList<Products>>
            ) {

                println("onResponse")
                response.body()?.let {
                            println("count18")
                            if(!isDone){
                                it.forEach {
                                    products.add(it)
                                }
                                isDone = true
                            }

                }
            }
        })
        products.forEach {
            println(it.id)
        }
    }

}