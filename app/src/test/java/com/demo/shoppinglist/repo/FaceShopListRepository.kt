package com.demo.shoppinglist.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.laureanti.shoppinglist.domain.ListItem
import com.laureanti.shoppinglist.domain.ShopItem
import com.laureanti.shoppinglist.domain.ShopListRepository

class FaceShopListRepository : ShopListRepository {

    private val shopItems = mutableListOf<ShopItem>()
    private val shopItemsLiveData = MutableLiveData<List<ShopItem>>(shopItems)
    private val _shopItemsLiveData: LiveData<List<ShopItem>> = shopItemsLiveData


    override suspend fun addShopItem(shopItem: ShopItem) {
        shopItems.add(shopItem)
        refreshData()
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopItems.remove(shopItem)
        refreshData()
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        refreshData()
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        return shopItems[shopItemId]
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopItemsLiveData
    }

    override fun getShopListWidthAds(): LiveData<List<ListItem>> {
        TODO()
    }

    private fun refreshData() {
        shopItemsLiveData.postValue(shopItems)
    }
}