package com.demo.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.demo.shoppinglist.data.ShopListRepositoryImpl
import com.demo.shoppinglist.domain.DeleteShopItemUseCase
import com.demo.shoppinglist.domain.EditShopItemUseCase
import com.demo.shoppinglist.domain.GetShopListUseCase
import com.demo.shoppinglist.domain.ShopItem

class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem){
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem){
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }
}