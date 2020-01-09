package com.adriantache.bigfivepersonalitytest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Dao for Room DB
 **/
@Dao
interface QuestionDao {
    @Query("SELECT `rowid`, `text`, `keyed`, `domain`, `set` FROM questions")
    suspend fun getAll(): List<QuestionEntity>

    @Query("SELECT `rowid`, `text`, `keyed`, `domain`, `set` FROM questions WHERE `set` LIKE :set")
    suspend fun findBySet(set: String): List<QuestionEntity>

    @Query("DELETE FROM questions")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) from questions")
    suspend fun countEntries() : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg questions: QuestionEntity)
}