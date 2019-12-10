package com.adriantache.bigfivepersonalitytest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.adriantache.bigfivepersonalitytest.adapters.JsonDomainAdapter
import com.adriantache.bigfivepersonalitytest.adapters.QuestionListAdapter
import com.adriantache.bigfivepersonalitytest.databinding.ActivityQuizBinding
import com.adriantache.bigfivepersonalitytest.json.LazyParser
import com.adriantache.bigfivepersonalitytest.models.Question
import com.adriantache.bigfivepersonalitytest.utils.ANSWER_SUMMARY
import com.adriantache.bigfivepersonalitytest.utils.JSON_FILE
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import okio.Okio.buffer
import okio.Okio.source
import java.io.FileNotFoundException
import java.lang.Runnable
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureNanoTime


//todo implement facets? => complexity
class QuizActivity : AppCompatActivity(), QuestionListAdapter.Interaction, CoroutineScope {
    companion object {
        private val TAG = QuizActivity::class.java.simpleName
    }

    //holds all questions as a regular array
    private lateinit var questions: List<Question>

    //selected question json filename
    private lateinit var filename: String

    //RecyclerView implementation
    private lateinit var questionsAdapter: QuestionListAdapter

    //DataBinding
    private lateinit var binding: ActivityQuizBinding

    //Coroutine Scope
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    //RecyclerView onclick action
    override fun onItemSelected(position: Int, selection: Int) {
        //update answer in list
        questions[position].answer = selection

        //update list in the adapter
        questionsAdapter.notifyItemChanged(position)

        //trigger post-click checks
        clickedRadio()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz)

        //init coroutine scope
        job = Job()

        //read JSON filename from intent
        filename = intent.getStringExtra(JSON_FILE) ?: ""

        if (TextUtils.isEmpty(filename)) {
            Log.e(TAG, "Error getting JSON filename!")
            finish()
        }

        //create list of questions from the file
        launch {
            //todo remove performance profiling
            Log.i(TAG, "Question JSON processing time: ${measureNanoTime { parseJson() } / 1_000_000f} ms")
        }

        //RecyclerView implementation
        initRecyclerView()

        //set submit button OnClickListener to pass data to results activity
        binding.submit.setOnClickListener {
            //calculate each of the dimensions based on the results
            val answerSummary = calculateResult()

            val intent = Intent(this, ResultsActivity::class.java).apply {
                putExtra(JSON_FILE, filename)
                putExtra(ANSWER_SUMMARY, answerSummary)
            }
            startActivity(intent)
        }

        Runnable {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                isImmersive = true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@QuizActivity)
            questionsAdapter = QuestionListAdapter(this@QuizActivity)
            adapter = questionsAdapter
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        //if available, enter immersive mode while in the Quiz activity
        if (hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            hideSystemUI()
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private suspend fun parseJson() {
        val moshi = Moshi.Builder()
                .add(JsonDomainAdapter())
                .add(KotlinJsonAdapterFactory())
                .build()

        binding.loading.visibility = View.VISIBLE

        try {
            withContext(Dispatchers.IO) {
                val inputStream = assets.open(filename)
                val bufferedSource = buffer(source(inputStream))
                val questionList = LazyParser(moshi).parse(JsonReader.of(bufferedSource)).toList().shuffled()

                //throw an exception if the list is empty
                if (questionList.isEmpty()) throw JsonDataException("No questions found!")

                //process the list, by adding IDs and making sure each sentence ends with a period
                questionList.forEachIndexed { index, question ->
                    if (question.text.takeLast(1) != ".") question.text += "."

                    //using this id for the RecyclerView differ
                    question.uniqueId = index
                }

                //ensure we close the BufferedSource, not sure if necessary
                bufferedSource.close()

                //assign the list to the class variable
                questions = questionList
            }
            //update UI
            binding.loading.visibility = View.GONE
            binding.labels.visibility = View.VISIBLE
            binding.progressBar.visibility = View.VISIBLE
            binding.progressBar.isIndeterminate = false
            binding.progressBar.max = 100
            binding.progressBar.progress = 0
            questionsAdapter.submitList(questions)
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "Cannot find JSON file $filename!")
            questions = emptyList()
            e.printStackTrace()
        } catch (e: JsonDataException) {
            Log.e(TAG, "Empty list returned from JSON!")
            questions = emptyList()
            e.printStackTrace()
        }
    }

    //when the user clicks a RadioButton, update the score
    private fun clickedRadio() {
        //activate and set up submit button if appropriate
        if (binding.submit.visibility != View.VISIBLE) {
            if (answeredAllQuestions()) {
                //scroll RecyclerView to bottom to match layout change
                binding.recyclerView.scrollToPosition(questions.size - 1)

                //hide progressBar
                binding.progressBar.visibility = View.GONE

                //show submit button to move to next activity
                binding.submit.visibility = View.VISIBLE
            } else {
                binding.progressBar.progress = answeredQuestions() / questions.size
            }
        }
    }

    private fun calculateResult(): HashMap<String, Int> {
        val answerMap = hashMapOf(
                "Openness" to 0,
                "Conscientiousness" to 0,
                "Extraversion" to 0,
                "Agreeableness" to 0,
                "Neuroticism" to 0
        )

        questions.forEach { question ->
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

    //seeing as how some companies use these as a way to test their candidates, I was curious if
    // I could create a weirdly average candidate
    private fun cheat() {
        //option 1: make all scores equal to the average (best option, basically tells you nothing)
        questions.forEach {
            it.answer = 3
        }

        //option 2: make all scores minim or maximum (reverse the if for maximum)
        questions.forEach {
            it.answer = if (it.keyed == "plus") 1
            else 5
        }

        //update UI to indicate which answers to pick (always middle ones for option 1)
        questionsAdapter.notifyDataSetChanged()
    }

    private fun answeredAllQuestions(): Boolean = !(questions.any { it.answer == 0 })

    private fun answeredQuestions(): Int = questions.filter { it.answer != 0 }.size * 100
}
