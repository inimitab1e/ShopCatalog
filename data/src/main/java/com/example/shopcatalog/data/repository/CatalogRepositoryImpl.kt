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
import kotlinx.coroutines.withContext
import java.lang.reflect.Type
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val context: Context,
    private val appDatabaseDAO: AppDatabaseDAO,
    private val ioDispatcher: CoroutineDispatcher
) : CatalogRepository {

    override suspend fun getListOfCatalogItems(): CatalogItemsList {
        val jsonString = context.assets.open("Catalog.json")
            .bufferedReader()
            .use { it.readText() }
        val gson = Gson()
        val objectFacAndSpecType: Type = object : TypeToken<CatalogItemsListDto>() {}.type
        val facAndSpec = gson.fromJson<CatalogItemsListDto>(jsonString, objectFacAndSpecType)
        return facAndSpec.toCatalogItemList()
    }

    override suspend fun getExistingCatalogItems(catalogItemName: String): CatalogItemInCart? =
        withContext(ioDispatcher) {
            val response =
                appDatabaseDAO.getCatalogItemsInCart(catalogItemName) ?: return@withContext null
            return@withContext response.toCatalogItemInCart()
        }

    override suspend fun addOneItemToCart(catalogItemName: String, count: String) {
        withContext(ioDispatcher) {
            appDatabaseDAO.addNewOrAddOneCatalogItem(catalogItemName, count)
        }
    }

    override suspend fun deleteOneItemFromCart(catalogItemName: String, count: String) {
        withContext(ioDispatcher) {
            appDatabaseDAO.removeOrDeleteCatalogItemFromCart(catalogItemName, count)
        }
    }

    override suspend fun clearAllCartInfo() {
        withContext(ioDispatcher) {
            appDatabaseDAO.deleteAllCartInfo()
        }
    }
}