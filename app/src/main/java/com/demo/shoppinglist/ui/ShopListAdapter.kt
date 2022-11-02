package com.demo.shoppinglist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.demo.shoppinglist.R
import com.demo.shoppinglist.domain.ListItem
import com.demo.shoppinglist.domain.ShopItem
import com.demo.shoppinglist.domain.TYPE_AD
import com.demo.shoppinglist.domain.TYPE_SHOP_ITEM
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ItemListViewHolder>() {

    var shopList = listOf<ListItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {

        return when (viewType) {
            VIEW_TYPE_DISABLED -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_shop_disabled, parent, false)
                return ShopItemViewHolder(view)
            }
            VIEW_TYPE_ENABLED -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_shop_enabled, parent, false)
                return ShopItemViewHolder(view)
            }
            VIEW_TYPE_AD -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.ad_row_layout, parent, false)
                return AdViewHolder(view)
            }
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(viewHolder: ItemListViewHolder, position: Int) {
        val itemType = shopList[position]

        if (itemType is ShopItem) {
            if (viewHolder is ShopItemViewHolder) {
                viewHolder.itemView.setOnLongClickListener {
                    onShopItemLongClickListener?.invoke(itemType)
                    true
                }
                viewHolder.itemView.setOnClickListener {
                    onShopItemClickListener?.invoke(itemType)
                }
                viewHolder.tvName.text = itemType.name
                viewHolder.tvCount.text = itemType.count.toString()
            }
        }
    }

    override fun onViewRecycled(viewHolder: ItemListViewHolder) {
        super.onViewRecycled(viewHolder)

        if (viewHolder is ShopItemViewHolder) {
            viewHolder.tvName.text = ""
            viewHolder.tvCount.text = ""
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun getItemViewType(position: Int): Int {
        val itemType = shopList[position].type

        val viewType = when (itemType) {
            TYPE_SHOP_ITEM -> {
                val shopItem = shopList[position]
                if (shopItem is ShopItem) {
                    return if (shopItem.enabled) {
                        VIEW_TYPE_ENABLED
                    } else {
                        VIEW_TYPE_DISABLED
                    }
                } else {
                    0
                }
            }
            TYPE_AD -> {
                return VIEW_TYPE_AD
            }
            else -> throw RuntimeException("Unknown view type: getItemViewType()")
        }
        return viewType
    }

    class ShopItemViewHolder(val view: View) : ItemListViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)

        override fun bind(item: ListItem) {

        }

    }

    class AdViewHolder(view: View) : ItemListViewHolder(view) {

        var adView = view.findViewById<AdView>(R.id.adView)

        override fun bind(item: ListItem) {
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
        }

    }

    interface OnShopItemLongClickListener {
        fun onShopItemLongClick(shopItem: ShopItem)
    }

    abstract class ItemListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: ListItem)
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101
        const val VIEW_TYPE_AD = 102
        const val MAX_POOL_SIZE = 30
    }
}