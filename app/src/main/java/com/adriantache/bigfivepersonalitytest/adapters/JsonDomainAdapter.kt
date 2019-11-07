package com.adriantache.bigfivepersonalitytest.adapters

import com.adriantache.bigfivepersonalitytest.ERROR
import com.adriantache.bigfivepersonalitytest.json.Domain
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson


/**
 * Converts domain chars to the corresponding full text
 **/
internal class JsonDomainAdapter {
    @ToJson
    fun toJson(@Domain domain: String): String {
        return when (domain) {
            "Openness" -> "O"
            "Conscientiousness" -> "C"
            "Extraversion" -> "E"
            "Agreeableness" -> "A"
            "Neuroticism" -> "N"
            else -> ERROR
        }
    }

    @FromJson
    @Domain
    fun fromJson(domain: String): String {
        return when (domain) {
            "O" -> "Openness"
            "C" -> "Conscientiousness"
            "E" -> "Extraversion"
            "A" -> "Agreeableness"
            "N" -> "Neuroticism"
            else -> ERROR
        }
    }
}