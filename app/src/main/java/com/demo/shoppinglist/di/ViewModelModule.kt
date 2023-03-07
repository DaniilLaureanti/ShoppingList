package com.demo.shoppinglist.di

import androidx.lifecycle.ViewModel
import com.demo.shoppinglist.ui.main.MainViewModel
import com.demo.shoppinglist.ui.shopitem.ShopItemViewModel
import com.demo.shoppinglist.ui.welcome.WelcomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun bindShopItemViewModel(viewModel: ShopItemViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    fun bindWelcomeViewModel(viewModel: WelcomeViewModel): ViewModel
}