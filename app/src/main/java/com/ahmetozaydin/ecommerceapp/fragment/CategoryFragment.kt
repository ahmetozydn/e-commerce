package com.ahmetozaydin.ecommerceapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.ecommerceapp.adapter.CategoriesAdapter
import com.ahmetozaydin.ecommerceapp.adapter.CategorizedProduct
import com.ahmetozaydin.ecommerceapp.databinding.FragmentCategoryBinding
import com.ahmetozaydin.ecommerceapp.viewmodel.CategoryViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CategoryFragment : Fragment(), CategoriesAdapter.Listener {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var viewModel: CategoryViewModel
    private lateinit var adapter: CategoriesAdapter
    private var categorizedAdapter: CategorizedProduct? = null
        private lateinit var layoutManager : RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        binding.recyclerViewCategory.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerViewCategorizedProduct.layoutManager = layoutManager

        runBlocking {
            launch {
                viewModel.getCategoryFromAPI()
                viewModel.getFirstCategoryFromAPI()
            }
        }
        viewModel.categoryList.observe(viewLifecycleOwner, Observer {

            println(it)
            viewModel.categoryList.value?.let {
                adapter = viewModel.categoryList.value?.let { it1 ->
                    CategoriesAdapter(
                        it1,
                        requireContext(),
                        this@CategoryFragment
                    )
                }!!
                binding.recyclerViewCategory.adapter = adapter
            }
            println("viewModel")
            println("size " + viewModel.categoryList.value?.size)
            println("observer")
        })
        viewModel.categorizedProducts.observe(viewLifecycleOwner, Observer {
            viewModel.categorizedProducts.value?.let {
                categorizedAdapter = CategorizedProduct(it, requireContext())
                binding.recyclerViewCategorizedProduct.adapter = categorizedAdapter
            }
        })
    }
    override fun onItemClick(position: Int, holder: CategoriesAdapter.PlaceHolder) {
            viewModel.getCategorizedProductFromAPI(holder.binding.buttonEachCategory.text.toString())
    }
}