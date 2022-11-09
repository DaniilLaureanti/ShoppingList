package com.demo.shoppinglist.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetShopListWidthAdsUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository
    ) {
    fun getShopListWidthAds(): LiveData<List<ListItem>> {
        return shopListRepository.getShopListWidthAds()
    }
}