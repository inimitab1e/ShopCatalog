package com.example.shopcatalog.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopcatalog.domain.model.CatalogItemsList
import com.example.shopcatalog.domain.repository.CatalogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val catalogRepository: CatalogRepository
) : ViewModel() {

    private val _catalogItemsList =
        MutableStateFlow<List<String>>(emptyList())
    val catalogItemsList: StateFlow<List<String>> =
        _catalogItemsList.asStateFlow()

    init {
        getCatalogItemsList()
    }

    private fun getCatalogItemsList() {
        viewModelScope.launch {
            val catalogItemsList = catalogRepository.getListOfCatalogItems()
            _catalogItemsList.tryEmit(catalogItemsList.catalogList)
        }
    }
}