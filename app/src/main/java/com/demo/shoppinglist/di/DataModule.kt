package com.demo.shoppinglist.di

import android.app.Application
import com.demo.shoppinglist.data.database.AppDataBase
import com.demo.shoppinglist.data.database.ShopListDao
import com.demo.shoppinglist.data.repository.ShopListRepositoryImpl
import com.demo.shoppinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object{

        @Provides
        @ApplicationScope
        fun provideShopListDao(application: Application): ShopListDao {
            return AppDataBase.getInstance(application).shopListDao()
        }
    }
}