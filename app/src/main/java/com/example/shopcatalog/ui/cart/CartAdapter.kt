package com.example.shopcatalog.ui.cart

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopcatalog.databinding.CartListItemBinding
import com.example.shopcatalog.domain.model.CatalogItemInCart
import com.example.shopcatalog.domain.utils.StringConstants

class CartAdapter : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    interface ButtonsClickListener {
        fun onDeleteFromCartButtonClickListener(itemName: String)
        fun onRemoveItemButtonClickListener(itemName: String, countItem: String)
        fun onAddItemButtonClickListener(itemName: String, countItem: String)
    }

    var cartList = mutableListOf<CatalogItemInCart>()
    var listener: ButtonsClickListener? = null

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
        setupRemoveButtonLogic(holder.ibRemoveItem, holder.itemName, holder.tvItemsCount)
        setupAddButtonLogic(holder.ibAddItem, holder.itemName, holder.tvItemsCount)
        setupDeleteButtonLogic(holder.btnDeleteItem, holder.itemName)
    }

    private fun setupRemoveButtonLogic(button: ImageButton, itemName: TextView, count: TextView) {
        if (count.text == StringConstants.minimumCatalogItemCountInCart) {
            button.setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN)
        } else {
            button.colorFilter = null
        }

        button.setOnClickListener {
            if (count.text != StringConstants.minimumCatalogItemCountInCart) {
                listener?.onRemoveItemButtonClickListener(
                    itemName.text.toString(), count.text.toString()
                )
            }
        }
    }

    private fun setupAddButtonLogic(button: ImageButton, itemName: TextView, count: TextView) {
        button.setOnClickListener {
            listener?.onAddItemButtonClickListener(itemName.text.toString(), count.text.toString())
        }
    }

    private fun setupDeleteButtonLogic(button: Button, itemName: TextView) {
        button.setOnClickListener {
            listener?.onDeleteFromCartButtonClickListener(itemName.text.toString())
        }
    }
}