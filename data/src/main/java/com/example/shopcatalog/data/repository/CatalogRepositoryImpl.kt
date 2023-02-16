package com.example.shopcatalog.data.repository

import android.content.Context
import com.example.shopcatalog.data.model.CatalogItemsListDto
import com.example.shopcatalog.data.toCatalogItemList
import com.example.shopcatalog.domain.model.CatalogItemsList
import com.example.shopcatalog.domain.repository.CatalogRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val context: Context
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
}