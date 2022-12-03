package com.ahmetozaydin.ecommerceapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.ecommerceapp.adapter.CategoryAdapter
import com.ahmetozaydin.ecommerceapp.adapter.ProductsAdapter
import com.ahmetozaydin.ecommerceapp.databinding.FragmentHomeBinding
import com.ahmetozaydin.ecommerceapp.model.BaseClass
import com.ahmetozaydin.ecommerceapp.model.Product
import com.ahmetozaydin.ecommerceapp.service.ProductsAPI
import com.ahmetozaydin.ecommerceapp.view.MainActivity.Companion.BASE_URL
import com.ahmetozaydin.ecommerceapp.viewmodel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment(), ProductsAdapter.Listener, CategoryAdapter.Listener {
    private var isDone: Boolean = false
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private var products = ArrayList<Product>()
    private var productsAdapter: ProductsAdapter? = null
    private var categoryAdapter: CategoryAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val layoutManager = GridLayoutManager(activity, 2)// oluyorsa layout managerları birleştir.
        binding.recyclerView.layoutManager = layoutManager

        fetchData()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        //viewModel.getDataFromUrl()
        observeLiveData()

    }
   private fun observeLiveData(){
    }

    private fun fetchData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductsAPI::class.java)
        activity?.runOnUiThread(Runnable {
            val call = service.getData()
            call.enqueue(object : Callback<BaseClass> {
                override fun onFailure(call: Call<BaseClass>, t: Throwable) {
                    println("error")
                    t.printStackTrace()
                    println(t)
                }

                override fun onResponse(
                    call: Call<BaseClass>,
                    response: Response<BaseClass>
                ) {
                    response.body()?.let {
                        if (!isDone) {
                            isDone = true
                            it.products?.forEach {
                                products.add(it)
                            }
                        }
                    }
                    productsAdapter = context?.let {
                        ProductsAdapter(
                            products,
                            requireActivity()
                        )
                    }
                    binding.recyclerView.adapter = productsAdapter
                    val horizontalLayoutManager: RecyclerView.LayoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    binding.recyclerViewCategories.layoutManager = horizontalLayoutManager
                    val categories = ArrayList<String>()
                    products.forEach {
                        if (!categories.contains(it.category) || categories.size == 0) {
                            it.category?.let { it1 -> categories.add(it1) }
                        }
                    }
                    categoryAdapter = context?.let { CategoryAdapter(products, categories, it) }
                    binding.recyclerViewCategories.adapter = categoryAdapter
                }
            })
        })
    }
    /* fun loadImageFromWeb(url: ArrayList<Product>?) {

          try {
             url?.forEach {
                 val `is`: InputStream = URL(it.image?.get(1)).content as InputStream
                 imageViews.add(Drawable.createFromStream(`is`, "src name"))
             }
         } catch (e: Exception) {
             e.printStackTrace()
         }
     }*/

    override fun onItemClick(products: Product) {
        Toast.makeText(activity, "item is clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        binding.recyclerViewCategories.adapter = categoryAdapter;

    }

    override fun categoryButtonClicked(
        products: ArrayList<Product>,
        holder: CategoryAdapter.PlaceHolder,
        position: Int
    ) {
        products.forEach {
            if (it.category == products[position].category) {
                println(it.category)
            }
        }
    }



}