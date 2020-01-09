package com.adriantache.bigfivepersonalitytest.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room database
 **/
@Database(entities = [QuestionEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}