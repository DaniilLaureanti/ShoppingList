package com.demo.shoppinglist.ui.shopitem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.demo.shoppinglist.R
import com.demo.shoppinglist.databinding.ItemShopDisabledBinding
import com.demo.shoppinglist.databinding.ItemShopEnabledBinding
import com.demo.shoppinglist.domain.ListItem
import com.demo.shoppinglist.domain.ShopItem
import com.demo.shoppinglist.domain.TYPE_AD
import com.demo.shoppinglist.domain.TYPE_SHOP_ITEM
import com.demo.shoppinglist.ui.AdItemViewHolder
import com.demo.shoppinglist.ui.shopitem.viewHolder.ItemListViewHolder
import com.demo.shoppinglist.ui.shopitem.viewHolder.ShopItemViewHolder

class ShopListAdapter : ListAdapter<ListItem, ItemListViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        return when (viewType) {
            VIEW_TYPE_DISABLED -> {
                val layout = R.layout.item_shop_disabled
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    LayoutInflater.from(parent.context),
                    layout,
                    parent,
                    false
                )
                return ShopItemViewHolder(binding)
            }
            VIEW_TYPE_ENABLED -> {
                val layout = R.layout.item_shop_enabled
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    LayoutInflater.from(parent.context),
                    layout,
                    parent,
                    false
                )
                return ShopItemViewHolder(binding)
            }
            ShopListAda.VIEW_TYPE_AD -> {
                val layout = R.layout.item_banner_ad
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    LayoutInflater.from(parent.context),
                    layout,
                    parent,
                    false
                )
                return AdItemViewHolder(binding)
            }
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(viewHolder: ItemListViewHolder, position: Int) {
        val item = getItem(position)
        val binding = viewHolder.binding

        if (item is ShopItem) {
            binding.root.setOnLongClickListener {
                onShopItemLongClickListener?.invoke(item)
                true
            }
            binding.root.setOnClickListener {
                onShopItemClickListener?.invoke(item)
            }
            when (binding) {
                is ItemShopDisabledBinding -> {
                    binding.shopItem = item
                }
                is ItemShopEnabledBinding -> {
                    binding.shopItem = item
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        when (currentList[position].type) {
            TYPE_SHOP_ITEM -> {
                return getShopItemType(getItem(position))
            }
            TYPE_AD -> {
                return VIEW_TYPE_AD
            }
        }
        throw RuntimeException("getItemViewType")
    }

    private fun getShopItemType(item: ListItem): Int {
        if (item is ShopItem) {
            return checkItemDisabledOrEnabled(item)
        }
        throw RuntimeException("getShopItemType")
    }

    private fun checkItemDisabledOrEnabled(shopItem: ShopItem): Int {
        return if (shopItem.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101
        const val VIEW_TYPE_AD = 102
        const val MAX_POOL_SIZE = 30
    }
}