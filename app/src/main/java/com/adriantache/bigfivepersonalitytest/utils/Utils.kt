package com.adriantache.bigfivepersonalitytest.utils

import androidx.lifecycle.MutableLiveData

/**
 * Various utility functions
 **/

object Utils {
    //necessary for triggering a manual LiveData update
    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}
