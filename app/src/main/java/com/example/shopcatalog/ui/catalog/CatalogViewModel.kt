package com.example.shopcatalog.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopcatalog.domain.model.CatalogItemsList
import com.example.shopcatalog.domain.repository.CatalogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val catalogRepository: CatalogRepository
) : ViewModel() {

    private val _catalogItemsList =
        MutableSharedFlow<CatalogItemsList>(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val catalogItemsList: SharedFlow<CatalogItemsList> =
        _catalogItemsList.asSharedFlow()

    init {
        getCatalogItemsList()
    }

    private fun getCatalogItemsList() {
        viewModelScope.launch {
            val catalogItemsList = catalogRepository.getListOfCatalogItems()
            _catalogItemsList.tryEmit(catalogItemsList)
        }
    }
}