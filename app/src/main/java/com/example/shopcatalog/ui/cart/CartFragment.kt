package com.example.shopcatalog.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.shopcatalog.R
import com.example.shopcatalog.databinding.FragmentCartBinding
import com.example.shopcatalog.extensions.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)
    private val cartViewModel: CartViewModel by viewModels()
    private val cartAdapter: CartAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CartAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initClickers()
    }

    override fun onStart() {
        super.onStart()

        cartViewModel.getCartItemsList()
    }

    private fun initRecyclerView() {
        binding.rwCartList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }

        cartViewModel.cartItemsList.onEach { cartItemsList ->
            if (cartItemsList.isEmpty()) {
                with(binding) {
                    rwCartList.isVisible = false
                    emptyCatalogContainer.isVisible = true
                }
            } else {
                with(binding) {
                    rwCartList.isVisible = true
                    emptyCatalogContainer.isVisible = false
                }
                cartAdapter.setCartItemsList(cartItemsList)
            }
        }.launchWhenStarted(lifecycleScope)
    }

    private fun initClickers() {
        binding.btnToCatalog.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_catalogFragment)
        }
    }
}