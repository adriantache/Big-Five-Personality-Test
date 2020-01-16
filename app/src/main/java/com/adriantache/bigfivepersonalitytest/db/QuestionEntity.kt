package com.adriantache.bigfivepersonalitytest.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import com.adriantache.bigfivepersonalitytest.models.Question
import com.adriantache.bigfivepersonalitytest.utils.ROOM_TABLE_NAME

/**
 * Entity class for Room DB
 **/
@Fts4
@Entity(tableName = ROOM_TABLE_NAME)
data class QuestionEntity(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rowid") val uniqueId: Int,
        @NonNull val text: String,
        @NonNull val keyed: String,
        @NonNull val domain: String,
        @NonNull val set: String
) {
    companion object {
        fun from(questionEntity: QuestionEntity): Question = Question(
                uniqueId = questionEntity.uniqueId,
                text = questionEntity.text,
                keyed = questionEntity.keyed,
                domain = questionEntity.domain
        )

        fun to(question: Question, set: String): QuestionEntity = QuestionEntity(
                uniqueId = question.uniqueId,
                text = question.text,
                keyed = question.keyed,
                domain = question.domain,
                set = set
        )
    }
}
