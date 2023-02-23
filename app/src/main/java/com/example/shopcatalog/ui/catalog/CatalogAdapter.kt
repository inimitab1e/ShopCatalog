package com.example.shopcatalog.ui.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopcatalog.databinding.CatalogListItemBinding

class CatalogAdapter : RecyclerView.Adapter<CatalogAdapter.ViewHolder>() {

    var onItemClick: ((String) -> Unit)? = null
    var catalogList = mutableListOf<String>()

    inner class ViewHolder(binding: CatalogListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvTest = binding.tvTest

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(catalogList[bindingAdapterPosition])
            }
        }
    }

    fun setCatalogItemsList(category: List<String>) {
        this.catalogList = category.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CatalogListItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = catalogList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val value = catalogList[position]
        holder.tvTest.text = value
    }
}