package com.example.shopcatalog.data.repository

import android.content.Context
import com.example.shopcatalog.data.model.CatalogItemsListDto
import com.example.shopcatalog.data.toCatalogItemInCart
import com.example.shopcatalog.data.toCatalogItemList
import com.example.shopcatalog.domain.local.AppDatabaseDAO
import com.example.shopcatalog.domain.model.CatalogItemInCart
import com.example.shopcatalog.domain.model.CatalogItemsList
import com.example.shopcatalog.domain.repository.CatalogRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.lang.reflect.Type
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val context: Context,
    private val appDatabaseDAO: AppDatabaseDAO,
    private val ioDispatcher: CoroutineDispatcher
) : CatalogRepository {

    override suspend fun getListOfCatalogItems(): CatalogItemsList =
        withContext(ioDispatcher) {
            val jsonString = context.assets.open("Catalog.json")
                .bufferedReader()
                .use { it.readText() }
            val gson = Gson()
            val objectFacAndSpecType: Type = object : TypeToken<CatalogItemsListDto>() {}.type
            val facAndSpec = gson.fromJson<CatalogItemsListDto>(jsonString, objectFacAndSpecType)
            return@withContext facAndSpec.toCatalogItemList()
        }

    override fun getCartSize(): Flow<Int> =
        appDatabaseDAO.getRowsCountOfCart()

    override fun getExistingCatalogItems(catalogItemName: String): Flow<CatalogItemInCart?> =
        appDatabaseDAO.getPickedCatalogItemInCart(catalogItemName).map { value ->
            value?.toCatalogItemInCart()
        }

    override fun getListOfCartItems(): Flow<List<CatalogItemInCart>> =
        appDatabaseDAO.getAllItemsFromCart().map { catalogItemsList ->
            catalogItemsList.map { cartItem -> cartItem.toCatalogItemInCart() }
        }

    override suspend fun addOneItemToCart(cartItemName: String, count: String) {
        withContext(ioDispatcher) {
            appDatabaseDAO.addNewOrAddOneCatalogItem(cartItemName, count)
        }
    }

    override suspend fun deleteOrDecreaseOneItemFromCart(cartItemName: String, count: String) {
        withContext(ioDispatcher) {
            appDatabaseDAO.removeOrDeleteCatalogItemFromCart(cartItemName, count)
        }
    }

    override suspend fun deleteItemFromCart(cartItemName: String) {
        withContext(ioDispatcher) {
            appDatabaseDAO.deleteCartItem(cartItemName)
        }
    }

    override suspend fun clearAllCartInfo() {
        withContext(ioDispatcher) {
            appDatabaseDAO.deleteAllCartInfo()
        }
    }
}