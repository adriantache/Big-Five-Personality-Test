package com.adriantache.bigfivepersonalitytest.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.adriantache.bigfivepersonalitytest.db.AppDatabase
import com.adriantache.bigfivepersonalitytest.db.QuestionEntity
import com.adriantache.bigfivepersonalitytest.models.Question
import com.adriantache.bigfivepersonalitytest.utils.Utils.notifyObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * ViewModel class for QuizActivity
 **/
class QuizViewModel(app: Application) : AndroidViewModel(app), CoroutineScope {
    companion object {
        val TAG = QuizViewModel::class.simpleName
    }

    //Coroutine Scope
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val questions = MutableLiveData<MutableList<Question>>()
    private lateinit var filename: String

    init {
        questions.value = mutableListOf()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun updateAnswer(position: Int, selection: Int) {
        val question = questions.value?.get(position) ?: return

        question.answer = selection

        questions.value?.set(position, question) ?: Log.e(TAG, "Error updating list")

        questions.notifyObserver() //hacky crap to force LiveData update
    }

    fun setFile(file: String) {
        filename = file

        //create list of questions from the file
        launch { getQuestionsFromDb() }
    }

    private suspend fun getQuestionsFromDb() {
        val questionDatabase = Room
                .databaseBuilder(getApplication(), AppDatabase::class.java, "database")
                .build()
        val questionDao = questionDatabase.questionDao()
        val entityList = questionDao.findBySet(filename)

        val temp = mutableListOf<Question>()

        entityList.forEach { questionEntity ->
            temp.add(QuestionEntity.from(questionEntity))
        }

        questions.value = temp
    }

    fun calculateResult(): HashMap<String, Int> {
        val answerMap = hashMapOf(
                "Openness" to 0,
                "Conscientiousness" to 0,
                "Extraversion" to 0,
                "Agreeableness" to 0,
                "Neuroticism" to 0
        )

        questions.value?.forEach { question ->
            val answer = if (question.keyed == "plus") {
                question.answer
            } else {
                5 - question.answer + 1
            }
            val previousValue = answerMap[question.domain] ?: -1

            if (previousValue == -1) Log.e(TAG, "Error calculating answer scores!")

            answerMap[question.domain] = answerMap[question.domain]?.plus(answer) ?: 0
        }

        return answerMap
    }

    fun answeredAllQuestions(): Boolean = !questions.value.isNullOrEmpty() && (questions.value?.any { it.answer == 0 })?.not() ?: false

    fun answeredQuestions(): Double = (questions.value?.count { it.answer != 0 } ?: 0).toDouble()
}