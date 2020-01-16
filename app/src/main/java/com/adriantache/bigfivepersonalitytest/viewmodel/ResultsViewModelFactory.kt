package com.adriantache.bigfivepersonalitytest.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by elabi3 on 16/01/2020.
 **/
/**
 * ViewModel factory for QuizViewModel to enable initialization with filename
 **/
class ResultsViewModelFactory(private val resultsMap: HashMap<String, Int>, private val filename: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultsViewModel::class.java)) {
            return ResultsViewModel(resultsMap, filename) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}