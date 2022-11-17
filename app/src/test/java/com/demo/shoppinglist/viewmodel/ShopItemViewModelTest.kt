package com.demo.shoppinglist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.shoppinglist.repo.FaceShopListRepository
import com.demo.shoppinglist.domain.AddShopItemUseCase
import com.demo.shoppinglist.domain.EditShopItemUseCase
import com.demo.shoppinglist.domain.GetShopItemUseCase
import com.demo.shoppinglist.getOrAwaitValueTest
import com.demo.shoppinglist.ui.shopitem.ShopItemViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShopItemViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ShopItemViewModel

    @Before
    fun setup() {
        viewModel = ShopItemViewModel(
            GetShopItemUseCase(FaceShopListRepository()),
            AddShopItemUseCase(FaceShopListRepository()),
            EditShopItemUseCase(FaceShopListRepository())
        )
    }

    @Test
    fun `insert item without name return error`() {
        viewModel.addShopItem("name", "")
        val value = viewModel.errorInputCount.getOrAwaitValueTest()
        assertThat(value).isEqualTo(true)
    }

    @Test
    fun `insert item without count return error`() {
        viewModel.addShopItem("", "2")
        val value = viewModel.errorInputName.getOrAwaitValueTest()
        assertThat(value).isEqualTo(true)
    }

}