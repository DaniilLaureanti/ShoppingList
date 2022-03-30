package com.demo.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.demo.shoppinglist.data.ShopListRepositoryImpl
import com.demo.shoppinglist.domain.AddShopItemUseCase
import com.demo.shoppinglist.domain.EditShopItemUseCase
import com.demo.shoppinglist.domain.GetShopItemUseCase
import com.demo.shoppinglist.domain.ShopItem
import kotlinx.coroutines.launch

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getShopItem(shopItemId: Int) {
        viewModelScope.launch {
            val item = getShopItemUseCase.getShopItem(shopItemId)
            _shopItem.postValue(item)
        }
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
            val name = parseName(inputName)
            val count = parseCount(inputCount)
            val fieldValid = validateInput(name, count)
            if (fieldValid) {
                viewModelScope.launch {
                    val shopItem = ShopItem(name, count, true)
                    addShopItemUseCase.addShopItem(shopItem)
                    finishWork()
            }
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
            val name = parseName(inputName)
            val count = parseCount(inputCount)
            val fieldValid = validateInput(name, count)
            if (fieldValid) {
                _shopItem.value?.let {
                    viewModelScope.launch {
                        val item = it.copy(name = name, count = count)
                        editShopItemUseCase.editShopItem(item)
                        finishWork()
                    }
                }
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.postValue(true)
            return false
        }
        if (count <= 0) {
            _errorInputCount.postValue(true)
            return false
        }
        return result
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    fun resetErrorInputCount() {
        _errorInputCount.postValue(false)
    }

    fun resetErrorInputName() {
        _errorInputName.postValue(false)
    }

    private fun finishWork() {
        _shouldCloseScreen.postValue(Unit)
    }
}