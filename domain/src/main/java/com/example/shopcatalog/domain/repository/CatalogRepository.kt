package com.example.shopcatalog.domain.repository

import com.example.shopcatalog.domain.model.CatalogItemInCart
import com.example.shopcatalog.domain.model.CatalogItemsList
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {
    suspend fun getListOfCatalogItems(): CatalogItemsList

    fun getCartSize(): Flow<Int>

    fun getExistingCatalogItems(catalogItemName: String): Flow<CatalogItemInCart?>

    suspend fun getListOfCartItems(): List<CatalogItemInCart>

    suspend fun addOneItemToCart(catalogItemName: String, count: String)

    suspend fun deleteOneItemFromCart(catalogItemName: String, count: String)

    suspend fun clearAllCartInfo()
}