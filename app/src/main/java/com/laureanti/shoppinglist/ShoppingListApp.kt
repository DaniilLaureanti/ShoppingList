package com.laureanti.shoppinglist

import android.app.Application
import com.laureanti.shoppinglist.di.DaggerApplicationComponent

class ShoppingListApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}