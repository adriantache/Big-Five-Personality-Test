package com.adriantache.bigfivepersonalitytest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.adriantache.bigfivepersonalitytest.adapters.JsonDomainAdapter
import com.adriantache.bigfivepersonalitytest.db.AppDatabase
import com.adriantache.bigfivepersonalitytest.db.QuestionDao
import com.adriantache.bigfivepersonalitytest.db.QuestionEntity
import com.adriantache.bigfivepersonalitytest.json.LazyParser
import com.adriantache.bigfivepersonalitytest.models.Question
import com.adriantache.bigfivepersonalitytest.utils.EXPECTED_DB_ENTRIES
import com.adriantache.bigfivepersonalitytest.utils.FILES
import com.adriantache.bigfivepersonalitytest.utils.JSON_STORED_IN_DB
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import okio.Okio
import java.io.FileNotFoundException
import kotlin.coroutines.CoroutineContext

class SplashActivity : AppCompatActivity(), CoroutineScope {
    companion object {
        private val TAG = SplashActivity::class.java.simpleName
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private lateinit var job: Job
    private lateinit var questionDao: QuestionDao
    private lateinit var questionDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        job = Job()

        checkDatabaseLoaded()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    private fun checkDatabaseLoaded() {
        //check SharedPrefs to see if database has been unpacked at first launch
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)

        questionDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database")
                .fallbackToDestructiveMigration()
                .build()
        questionDao = questionDatabase.questionDao()

        val updateJob = launch {
            if (sharedPref.getBoolean(JSON_STORED_IN_DB, false) &&
                    withContext(Dispatchers.IO) { questionDao.countEntries() } == EXPECTED_DB_ENTRIES) {
                startApp()
                return@launch
            }

            Toast.makeText(this@SplashActivity, "First launch: Loading questions from files.", Toast.LENGTH_LONG).show()

            //populate database from JSON files
            withContext(Dispatchers.IO) { unpackJsonToDatabase() }
        }

        launch {
            updateJob.join() //wait for database ops to finish

            val entries = withContext(Dispatchers.IO) { questionDao.countEntries() }

            if (entries == EXPECTED_DB_ENTRIES) {
                with(sharedPref.edit()) {
                    putBoolean(JSON_STORED_IN_DB, true)
                    apply()
                }

                startApp()
            } else { //We have failed at finding AND at saving entries to DB, SOS!
                Log.e(TAG, "Unexpected number of entries in database! ($entries)")
                //todo rethink this, it's inelegant. maybe implement fallback to json?
            }
        }
    }

    private suspend fun unpackJsonToDatabase() {
        var count = 0 //todo refactor this out

        withContext(Dispatchers.IO) {
            questionDao.deleteAll() //empty DB first

            for (file in FILES) {
                //get question list from JSON file
                val questions = parseJson(file)

                if (questions.isEmpty()) {
                    Log.e(TAG, "JSON parser returned no results!")
                    continue
                }

                questions.forEach {
                    questionDao.insertAll(QuestionEntity.convertQuestionToEntity(it, file))
                    count++
                }

                Log.i(TAG, "Stored $count/$EXPECTED_DB_ENTRIES questions.")
            }
        }
    }

    private suspend fun parseJson(filename: String): List<Question> {
        val moshi = Moshi.Builder()
                .add(JsonDomainAdapter())
                .add(KotlinJsonAdapterFactory())
                .build()

        var questionList: List<Question> = emptyList()

        try {
            withContext(Dispatchers.IO) {
                val inputStream = assets.open(filename)
                val bufferedSource = Okio.buffer(Okio.source(inputStream))
                questionList = LazyParser(moshi).parse(JsonReader.of(bufferedSource)).toList().shuffled()

                //throw an exception if the list is empty
                if (questionList.isEmpty()) throw JsonDataException("No questions found!")

                //process the list, by adding IDs and making sure each sentence ends with a period
                questionList.forEach { question ->
                    if (question.text.takeLast(1) != ".") question.text += "."
                }

                //ensure we close the BufferedSource, not sure if necessary
                bufferedSource.close()
            }
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "Cannot find JSON file $filename!")
            e.printStackTrace()
        } catch (e: JsonDataException) {
            Log.e(TAG, "Empty list returned from JSON!")
            e.printStackTrace()
        }

        return questionList
    }

    private fun startApp() {
        // Start home activity
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        // close splash activity
        finish()
    }
}
