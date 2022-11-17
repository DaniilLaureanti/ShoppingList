package com.demo.shoppinglist.ui.shopitem

import androidx.recyclerview.widget.DiffUtil
import com.demo.shoppinglist.domain.ListItem
import com.demo.shoppinglist.domain.ShopItem

class ShopItemDiffCallback : DiffUtil.ItemCallback<ListItem>() {

    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        if (oldItem is ShopItem && newItem is ShopItem) {
            return oldItem.id == newItem.id
        }
//        return throw RuntimeException("DiffCallback")
        return oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        if (oldItem is ShopItem && newItem is ShopItem) {
            return oldItem == newItem
        }
//        return throw RuntimeException("DiffCallback")
        return oldItem.equals(newItem)
    }

}