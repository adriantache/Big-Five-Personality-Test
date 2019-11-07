package com.adriantache.bigfivepersonalitytest.json

import com.adriantache.bigfivepersonalitytest.models.Question
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi

/**
 * Code adapted from: https://medium.com/@BladeCoder/advanced-json-parsing-techniques-using-moshi-and-kotlin-daf56a7b963d
 **/
class LazyParser(moshi: Moshi) {
    private val personAdapter: JsonAdapter<Question> = moshi.adapter(Question::class.java)

    fun parse(reader: JsonReader): Sequence<Question> {
        return sequence {
            reader.readArray {
                yield(personAdapter.fromJson(reader)!!)
            }
        }
    }

    private inline fun JsonReader.readArray(body: () -> Unit) {
        beginArray()
        while (hasNext()) {
            body()
        }
        endArray()
    }
}