package com.example.shopcatalog.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopcatalog.databinding.CartListItemBinding
import com.example.shopcatalog.domain.model.CatalogItemInCart

class CartAdapter : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    var cartList = mutableListOf<CatalogItemInCart>()

    class ViewHolder(binding: CartListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemName = binding.tvCartItemName
        val tvItemsCount = binding.tvCountOfItems
        val ibAddItem = binding.ibAddItemToCart
        val ibRemoveItem = binding.ibRemoveItemToCart
        val btnDeleteItem = binding.btnDeleteFromCart
    }

    fun setCartItemsList(cartItem: List<CatalogItemInCart>) {
        this.cartList = cartItem.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CartListItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = cartList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val value = cartList[position]
        holder.itemName.text = value.catalogItemName
        holder.tvItemsCount.text = value.catalogItemCount
    }
}