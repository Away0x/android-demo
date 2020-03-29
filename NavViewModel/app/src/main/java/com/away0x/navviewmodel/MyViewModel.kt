package com.away0x.navviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {

    private var number: MutableLiveData<Int> = MutableLiveData()

    fun getNumber(): MutableLiveData<Int> {
        if (number.value == null) {
            number.value = 0
        }

        return number
    }

    fun add(x: Int) {
        number.value = getNumber().value!! + x

        if (number.value!! < 0) {
            number.value = 0
        }
    }

}