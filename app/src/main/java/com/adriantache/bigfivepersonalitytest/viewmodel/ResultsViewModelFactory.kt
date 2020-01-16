package com.adriantache.bigfivepersonalitytest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * ViewModel factory for QuizViewModel to enable initialization with filename
 **/
class ResultsViewModelFactory(private val resultsMap: HashMap<String, Int>,
                              private val filename: String) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultsViewModel::class.java)) {
            return ResultsViewModel(resultsMap, filename) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}