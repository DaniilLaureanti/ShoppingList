package com.laureanti.shoppinglist.di

import android.app.Application
import com.laureanti.shoppinglist.data.provider.ShopItemProvider
import com.laureanti.shoppinglist.ui.main.MainActivity
import com.laureanti.shoppinglist.ui.shopitem.ShopItemFragment
import com.laureanti.shoppinglist.ui.welcome.WelcomeFragment
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