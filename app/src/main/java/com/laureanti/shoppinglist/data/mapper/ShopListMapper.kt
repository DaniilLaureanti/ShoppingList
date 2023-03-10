package com.laureanti.shoppinglist.data.mapper

import com.laureanti.shoppinglist.data.database.ShopItemDbModel
import com.laureanti.shoppinglist.domain.BannerAd
import com.laureanti.shoppinglist.domain.ListItem
import com.laureanti.shoppinglist.domain.ShopItem
import javax.inject.Inject

class ShopListMapper @Inject constructor() {
    fun mapEntityToDbModel(shopItem: ShopItem) = ShopItemDbModel(
        id = shopItem.id,
        name = shopItem.name,
        count = shopItem.count,
        enabled = shopItem.enabled
    )

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel) = ShopItem(
        id = shopItemDbModel.id,
        name = shopItemDbModel.name,
        count = shopItemDbModel.count,
        enabled = shopItemDbModel.enabled
    )

    fun mapListDbModelToListEntity(list: List<ShopItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }

    fun mapListDbModelToListEntityWidthAds(list: List<ShopItemDbModel>): List<ListItem> {
        val listEntity = mapListDbModelToListEntity(list)
        var count = 0
        val listItem = mutableListOf<ListItem>()
        for (shopItem in listEntity) {
            count++
            if (count % 3 == 0) {
                listItem.add(BannerAd())
            }
            listItem.add(shopItem)
        }
        return listItem
    }
}
