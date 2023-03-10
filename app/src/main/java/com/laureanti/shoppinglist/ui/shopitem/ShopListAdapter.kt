package com.laureanti.shoppinglist.ui.shopitem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.laureanti.shoppinglist.R
import com.laureanti.shoppinglist.databinding.ItemShopDisabledBinding
import com.laureanti.shoppinglist.databinding.ItemShopEnabledBinding
import com.laureanti.shoppinglist.domain.*
import com.laureanti.shoppinglist.ui.AdViewHolder
import com.laureanti.shoppinglist.ui.shopitem.viewHolder.ItemListViewHolder
import com.laureanti.shoppinglist.ui.shopitem.viewHolder.ShopItemViewHolder
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

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
            VIEW_TYPE_AD -> {
                val layout = R.layout.item_banner_ad
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    LayoutInflater.from(parent.context),
                    layout,
                    parent,
                    false
                )
                return AdViewHolder(binding)
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
        } else if (item is BannerAd){
            createAd(binding)
        }
    }

    private fun createAd(binding: ViewDataBinding) {

        val adView: AdView = binding.root.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        adView.adListener = object : AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                println(adError)
                // Code to be executed when an ad request fails.
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                println("load")
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
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