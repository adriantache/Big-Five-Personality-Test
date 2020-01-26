package com.adriantache.bigfivepersonalitytest.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.adriantache.bigfivepersonalitytest.db.AppDatabase
import com.adriantache.bigfivepersonalitytest.db.QuestionEntity
import com.adriantache.bigfivepersonalitytest.models.Question
import com.adriantache.bigfivepersonalitytest.utils.Utils.notifyObserver
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * ViewModel class for QuizActivity
 **/
class QuizViewModel(private val filename: String, app: Application) : AndroidViewModel(app), CoroutineScope {
    companion object {
        val TAG = QuizViewModel::class.simpleName
    }

    //Coroutine Scope
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val _questions = MutableLiveData<MutableList<Question>>()
    val questions: LiveData<MutableList<Question>>
        get() = _questions

    init {
        _questions.value = mutableListOf()

        //get list of questions from db that match filename
        launch { getQuestionsFromDb() }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun updateAnswer(position: Int, selection: Int) {
        val question = questions.value?.get(position) ?: return

        question.answer = selection

        _questions.value?.set(position, question) ?: Log.e(TAG, "Error updating list")

        _questions.notifyObserver() //hacky crap to force LiveData update
    }

    private suspend fun getQuestionsFromDb() {
        val temp = mutableListOf<Question>()

        withContext(Dispatchers.IO) {
            val questionDatabase = AppDatabase.getInstance(getApplication())
            val questionDao = questionDatabase.questionDao()
            val entityList = questionDao.findBySet(filename)

            entityList.forEach { questionEntity ->
                temp.add(QuestionEntity.from(questionEntity))
            }
        }

        _questions.value = temp
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

    //returning a double to make calculations easier
    fun answeredQuestions(): Double = (questions.value?.count { it.answer != 0 } ?: 0).toDouble()
}
