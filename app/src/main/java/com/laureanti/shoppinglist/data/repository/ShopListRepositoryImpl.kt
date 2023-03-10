package com.laureanti.shoppinglist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.laureanti.shoppinglist.data.database.ShopListDao
import com.laureanti.shoppinglist.data.mapper.ShopListMapper
import com.laureanti.shoppinglist.domain.ListItem
import com.laureanti.shoppinglist.domain.ShopItem
import com.laureanti.shoppinglist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper
) : ShopListRepository {

    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = Transformations
        .map(shopListDao.getShopList()){
        mapper.mapListDbModelToListEntity(it)
    }

    override fun getShopListWidthAds(): LiveData<List<ListItem>> = Transformations
        .map(shopListDao.getShopList()){
            mapper.mapListDbModelToListEntityWidthAds(it)
        }

}