package com.demo.shoppinglist.ui

import androidx.databinding.ViewDataBinding
import com.demo.shoppinglist.R
import com.demo.shoppinglist.ui.shopitem.viewHolder.IAdBanner
import com.demo.shoppinglist.ui.shopitem.viewHolder.ItemListViewHolder
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class AdItemViewHolder(override val binding: ViewDataBinding)
    : ItemListViewHolder(binding),
    IAdBanner {
    var adView: AdView = binding.root.findViewById(R.id.adView)
    override fun bind() {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}

