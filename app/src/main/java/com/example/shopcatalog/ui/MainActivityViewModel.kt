package com.example.shopcatalog.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopcatalog.domain.model.CatalogItemInCart
import com.example.shopcatalog.domain.repository.CatalogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val catalogRepository: CatalogRepository
) : ViewModel() {

    private val _cartItemCount =
        MutableStateFlow(cartSizeDefaultValue)
    val cartItemCount: StateFlow<Int> get() = _cartItemCount.asStateFlow()

    init {
        viewModelScope.launch {
            catalogRepository.getCartSize().collect { cartSize ->
                _cartItemCount.value = cartSize
            }
        }
    }

    companion object {
        const val cartSizeDefaultValue = 0
    }
}