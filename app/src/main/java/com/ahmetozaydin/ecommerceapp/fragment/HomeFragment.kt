package com.ahmetozaydin.ecommerceapp.fragment

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.ecommerceapp.adapter.CategoryAdapter
import com.ahmetozaydin.ecommerceapp.adapter.ProductsAdapter
import com.ahmetozaydin.ecommerceapp.databinding.FragmentHomeBinding
import com.ahmetozaydin.ecommerceapp.model.Product
import com.ahmetozaydin.ecommerceapp.viewmodel.HomeViewModel
import java.util.*


class HomeFragment : Fragment(), ProductsAdapter.Listener, CategoryAdapter.Listener {
    private var isDone: Boolean = false
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private var matchedProduct: ArrayList<Product> = arrayListOf()
    private var products = ArrayList<Product>()
    private lateinit var productsAdapter: ProductsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val layoutManager = GridLayoutManager(activity, 2)// oluyorsa layout managerları birleştir.
        binding.recyclerView.layoutManager = layoutManager
        //fetchData()
        binding.searchBarProduct.onActionViewExpanded()
        binding.searchBarProduct.clearFocus()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        //viewModel.getDataFromUrl()
        viewModel.getData(requireContext())
        viewModel.products.observe(viewLifecycleOwner, androidx.lifecycle.Observer { products ->
            products.let {
                productsAdapter = products?.let { it1 ->
                    ProductsAdapter(
                        it1,
                        requireContext() // TODO Fragment HomeFragment not attached to a context.
                    )
                }!!
                binding.recyclerView.adapter = productsAdapter
            }

        })
        binding.searchBarProduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                search(newText)
                return true
            }
        })
        var state = 1

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                state = newState
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && (state == 0 || state == 2)) {
                    binding.collapsingToolBarLayout.visibility = View.GONE

                } else if (dy < -10) {
                    binding.collapsingToolBarLayout.visibility = View.VISIBLE
                }
            }
        })
    }

/*
    private fun fetchData() {
        val retrofit = Retrofit
            .Builder()
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
                    response.body()?.let { it ->
                        if (!isDone) {
                            isDone = true
                            it.products?.forEach {
                                products.add(it)
                            }
                        }
                    }
                    productsAdapter = ProductsAdapter(
                        products,
                        requireContext() // TODO Fragment HomeFragment not attached to a context.
                    )
                    binding.recyclerView.adapter = productsAdapter
                    //val horizontalLayoutManager: RecyclerView.LayoutManager =
                    //   LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    //binding.recyclerViewCategories.layoutManager = horizontalLayoutManager
                    //val categories = ArrayList<String>()
                    */
/*products.forEach {
                        if (!categories.contains(it.category) || categories.size == 0) {
                            it.category?.let { it1 -> categories.add(it1) }
                        }
                    }*//*

                    //categoryAdapter = context?.let { CategoryAdapter(products, categories, it) }
                    //binding.recyclerViewCategories.adapter = categoryAdapter
                    // binding.searchBarProduct.isSubmitButtonEnabled = true
                }
            })
        })
    }
*/

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

    /* override fun onResume() {
         super.onResume()
         binding.recyclerViewCategories.adapter = categoryAdapter;

     }*/
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

    fun search(text: String?) { //alternative to it filter(),guava(). make inside viewModel
        matchedProduct = arrayListOf()
        text?.let {
            products.forEach { product ->
                if ((product.title?.contains(text, true) == true) ||
                    product.description.toString().contains(text, true) ||
                    product.category.toString().contains(text, true) ||
                    product.brand.toString().contains(text, true)
                ) {
                    matchedProduct.add(product)
                }
            }
            updateRecyclerView()
            if (matchedProduct.isEmpty()) {
                binding.linearLayoutEmptySearchMessage.visibility = View.VISIBLE

            } else {
                binding.linearLayoutEmptySearchMessage.visibility = View.INVISIBLE
            }
            updateRecyclerView()
        }
    }

    private fun updateRecyclerView() {
        binding.recyclerView.apply {
            productsAdapter = ProductsAdapter(
                matchedProduct,
                requireContext()
            )
            binding.recyclerView.adapter = productsAdapter
            // productsAdapter!!.notifyDataSetChanged()
        }
    }
}