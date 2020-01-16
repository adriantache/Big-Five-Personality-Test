package com.adriantache.bigfivepersonalitytest.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * ViewModel factory for QuizViewModel to enable initialization with filename
 **/
class QuizViewModelFactory(private val filename: String,
                           private val app: Application) : ViewModelProvider.AndroidViewModelFactory(app) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            return QuizViewModel(filename, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}