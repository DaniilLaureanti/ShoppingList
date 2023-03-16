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
        var listItemCounter = INITIAL_VALUE_COUNTER
        val listItem = mutableListOf<ListItem>()

        for (shopItem in listEntity) {
            listItemCounter++
            if (listItemCounter == FIRST_ELEMENT || listItemCounter % ADS_EVERY_ELEMENTS == 0) {
                listItem.add(BannerAd())
            }
            listItem.add(shopItem)
        }
        return listItem
    }

    companion object {
        private const val INITIAL_VALUE_COUNTER = 0
        private const val FIRST_ELEMENT = 1
        private const val ADS_EVERY_ELEMENTS = 15
    }

}
