package com.example.shopcatalog.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopcatalog.domain.model.CatalogItemInCart
import com.example.shopcatalog.domain.repository.CatalogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val catalogRepository: CatalogRepository
) : ViewModel() {

    private val _cartItemsList =
        MutableSharedFlow<List<CatalogItemInCart>>(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val cartItemsList: SharedFlow<List<CatalogItemInCart>> get() = _cartItemsList.asSharedFlow()

    fun getCartItemsList() {
        viewModelScope.launch {
            _cartItemsList.tryEmit(catalogRepository.getListOfCartItems())
        }
    }
}