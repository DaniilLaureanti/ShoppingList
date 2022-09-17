package com.demo.shoppinglist.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.demo.shoppinglist.data.database.AppDataBase
import com.demo.shoppinglist.data.database.ShopItemDbModel
import com.demo.shoppinglist.data.database.ShopListDao
import com.demo.shoppinglist.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
@ExperimentalCoroutinesApi
class ShopListDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var database: AppDataBase
    private lateinit var dao: ShopListDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDataBase::class.java
        )
            .allowMainThreadQueries() //this is a test case, we don't want other thread pools
            .build()

        dao = database.shopListDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertArtTesting() = runTest {
        val shopItem = ShopItemDbModel(3, "Name", 2, true)
        dao.addShopItem(shopItem)

        val list = dao.getShopList().getOrAwaitValue()
        assertThat(list).contains(shopItem)
    }

    @Test
    fun deleteArtTesting() = runTest {
        val shopItem = ShopItemDbModel(3, "Name", 2, true)
        dao.addShopItem(shopItem)
        dao.deleteShopItem(shopItem.id)

        val list = dao.getShopList().getOrAwaitValue()
        assertThat(list).doesNotContain(shopItem)

    }
}