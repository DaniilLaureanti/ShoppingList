package com.demo.shoppinglist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class WelcomeViewModel @Inject constructor() : ViewModel() {

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun finishWork() {
        _shouldCloseScreen.postValue(Unit)
    }
}