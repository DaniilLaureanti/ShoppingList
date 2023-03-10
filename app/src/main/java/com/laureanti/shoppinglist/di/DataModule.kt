package com.laureanti.shoppinglist.di

import android.app.Application
import com.laureanti.shoppinglist.data.database.AppDataBase
import com.laureanti.shoppinglist.data.database.ShopListDao
import com.laureanti.shoppinglist.data.repository.ShopListRepositoryImpl
import com.laureanti.shoppinglist.domain.ShopListRepository
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