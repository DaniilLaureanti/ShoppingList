package com.demo.shoppinglist.domain

const val TYPE_SHOP_ITEM = 0
const val TYPE_AD = 1

open class ListItem(val type: Int)

data class ShopItem(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID
) : ListItem(TYPE_SHOP_ITEM) {

    companion object {
        const val UNDEFINED_ID = 0
    }

}

class BannerAd : ListItem(TYPE_AD)
