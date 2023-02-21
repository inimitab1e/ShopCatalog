package com.example.shopcatalog.domain.local

import android.net.Uri
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.shopcatalog.domain.local.entities.Cart
import com.example.shopcatalog.domain.local.entities.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDatabaseDAO {

    @Insert
    fun importUserInfo(userInfo: Users)

    @Query("SELECT * from users where email = :email")
    fun fetchAllInfo(email: String): Flow<Users>

    @Query("UPDATE users SET gender = :value WHERE email = :email")
    suspend fun updateUserGender(value: String, email: String)

    @Query("UPDATE users SET phoneNumber = :value WHERE email = :email")
    suspend fun updateUserPhoneNumber(value: String, email: String)

    @Query("UPDATE users SET profileImage = :value WHERE email = :email")
    suspend fun updateUserProfileImage(value: Uri, email: String)

    @Query ("SELECT * from cart where catalogItemName = :catalogItemName")
    fun getCatalogItemsInCart(catalogItemName: String): Flow<Cart?>

    @Insert
    suspend fun addNewToCart(newCatalogItem: Cart)

    @Query("DELETE from cart")
    suspend fun deleteAllCartInfo()

    @Query("DELETE from cart where catalogItemName = :catalogItemName")
    suspend fun deleteCatalogItem(catalogItemName: String)

    @Query("UPDATE cart SET catalogItemCount = :count where catalogItemName = :catalogItemName")
    suspend fun addOneToCart(catalogItemName: String, count: String)

    @Query("UPDATE cart SET catalogItemCount = :count where catalogItemName = :catalogItemName")
    suspend fun removeOneFromCart(catalogItemName: String, count: String)

    @Transaction
    suspend fun removeOrDeleteCatalogItemFromCart(catalogItemName: String, count: String) {
        if (count == "0") {
            deleteCatalogItem(catalogItemName)
        } else {
            removeOneFromCart(catalogItemName, count)
        }
    }

    @Transaction
    suspend fun addNewOrAddOneCatalogItem(catalogItemName: String, count: String) {
        if (count == "1") {
            addNewToCart(Cart(catalogItemName = catalogItemName, catalogItemCount = count))
        } else {
            addOneToCart(catalogItemName, count)
        }
    }
}