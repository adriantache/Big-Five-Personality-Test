package com.adriantache.bigfivepersonalitytest

data class Question(
        val id: Int,
        val text: String,
        val keyed: String,
        val domain: String,
        var answer: Int = 0
)