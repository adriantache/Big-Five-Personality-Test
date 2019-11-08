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
import okio.Okio.buffer
import okio.Okio.source
import java.io.FileNotFoundException


//todo implement facets? => complexity
//todo [IMPORTANT] move JSON decoding off main thread
//  add loading indicator (if it takes a long time)
class QuizActivity : AppCompatActivity(), QuestionListAdapter.Interaction {
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

        //read JSON filename from intent
        filename = intent.getStringExtra(JSON_FILE) ?: ""

        if (TextUtils.isEmpty(filename)) {
            Log.e(TAG, "Error getting JSON filename!")
            finish()
        }

        //parse appropriate json file
        questions = parseJson()

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

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@QuizActivity)
            questionsAdapter = QuestionListAdapter(this@QuizActivity)
            adapter = questionsAdapter
        }

        questionsAdapter.submitList(questions)
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

    private fun parseJson(): List<Question> {
        val moshi = Moshi.Builder()
                .add(JsonDomainAdapter())
                .add(KotlinJsonAdapterFactory())
                .build()

        try {
            val inputStream = assets.open(filename)
            val bufferedSource = buffer(source(inputStream))
            val questionList = LazyParser(moshi).parse(JsonReader.of(bufferedSource)).toList().shuffled()

            //process the list, by adding IDs and making sure each sentence ends with a period
            questionList.forEachIndexed { index, question ->
                if (question.text.takeLast(1) != ".") question.text += "."

                //using this id for the RecyclerView differ
                question.uniqueId = index
            }

            //ensure we close the BufferedSource, not sure if necessary
            bufferedSource.close()

            //if all is okay, return the shuffled list
            if (questionList.isNotEmpty()) {
                return questionList
            } else throw JsonDataException("No questions found!")
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "Cannot find JSON file $filename!")
            e.printStackTrace()
        } catch (e: JsonDataException) {
            e.printStackTrace()
        }

        return emptyList()
    }

    //when the user clicks a RadioButton, update the score
    private fun clickedRadio() {
        //activate and set up submit button if appropriate
        if (binding.submit.visibility != View.VISIBLE && answeredAllQuestions()) {
            //scroll RecyclerView to bottom to match layout change
            binding.recyclerView.scrollToPosition(questions.size - 1)

            //show submit button to move to next activity
            binding.submit.visibility = View.VISIBLE
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

    private fun answeredAllQuestions(): Boolean = !(questions.any { it.answer == 0 })
}
