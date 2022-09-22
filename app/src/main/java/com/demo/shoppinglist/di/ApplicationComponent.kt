package com.demo.shoppinglist.di

import android.app.Application
import com.demo.shoppinglist.data.provider.ShopItemProvider
import com.demo.shoppinglist.ui.MainActivity
import com.demo.shoppinglist.ui.ShopItemFragment
import com.demo.shoppinglist.ui.WelcomeFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(shopItemFragment: ShopItemFragment)

    fun inject(welcomeFragment: WelcomeFragment)

    fun inject(shopItemProvider: ShopItemProvider)

    @Component.Factory
    interface Factory{

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}