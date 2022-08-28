package com.demo.shoppinglist.di

import android.app.Application
import com.demo.shoppinglist.presentation.MainActivity
import com.demo.shoppinglist.presentation.ShopItemActivity
import com.demo.shoppinglist.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(shopItemFragment: ShopItemFragment)

    @Component.Factory
    interface Factory{

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}