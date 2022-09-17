package com.demo.shoppinglist.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.shoppinglist.domain.ShopItem
import com.demo.shoppinglist.domain.ShopListRepository

class FaceShopListRepository: ShopListRepository {

    private val shopItems = mutableListOf<ShopItem>()
    private val shotItemsLiveData = MutableLiveData<List<ShopItem>>(shopItems)


    override suspend fun addShopItem(shopItem: ShopItem) {
        shopItems.add(shopItem)
        refreshData()
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopItems.remove(shopItem)
        refreshData()
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        //
        refreshData()
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        return shopItems[shopItemId]
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shotItemsLiveData
    }

    private fun refreshData(){
        shotItemsLiveData.postValue(shopItems)
    }
}