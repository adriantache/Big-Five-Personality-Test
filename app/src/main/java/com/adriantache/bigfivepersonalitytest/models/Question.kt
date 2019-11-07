package com.adriantache.bigfivepersonalitytest.models

import com.adriantache.bigfivepersonalitytest.json.Domain
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Question(
        var uniqueId: Int = 0,
        var text: String = "",
        val keyed: String = "",
        @Domain val domain: String = "",
        var answer: Int = 0
)
