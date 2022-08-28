package com.demo.shoppinglist

import android.app.Application
import com.demo.shoppinglist.di.DaggerApplicationComponent

class ShoppingListApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}