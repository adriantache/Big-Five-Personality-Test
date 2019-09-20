package com.adriantache.bigfivepersonalitytest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_quiz.*
import org.json.JSONArray
import java.io.IOException
import java.nio.charset.Charset


const val ANSWER_SUMMARY = "ANSWER_SUMMARY"

//todo implement facets? => complexity
class QuizActivity : AppCompatActivity(), QuestionListAdapter.Interaction {
    companion object {
        private val TAG = QuizActivity::class.java.simpleName
    }

    //holds all questions as a regular array
    private lateinit var questions: List<Question>

    private lateinit var filename: String

    //RecyclerView implementation
    private lateinit var questionListAdapter: QuestionListAdapter

    override fun onItemSelected() {
        clickedRadio()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        //read JSON filename from intent
        filename = intent.getStringExtra(JSON_FILE)

        if (TextUtils.isEmpty(filename)) {
            Log.e(TAG, "Error getting JSON filename!")
            finish()
        }

        //parse appropriate json file
        questions = parseJson(loadJSONFromAsset(filename))

        //generate the layout
        if (questions.isNotEmpty()) {
            //RecyclerView implementation
            initRecyclerView()
        } else {
            Log.e(TAG, "Error getting Question array!")
            finish()
        }

        Runnable {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                isImmersive = true
            }
        }
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@QuizActivity)
            questionListAdapter = QuestionListAdapter(this@QuizActivity)
            adapter = questionListAdapter
        }
        questionListAdapter.submitList(questions)
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

    private fun loadJSONFromAsset(filename: String): String {
        return try {
            val inputStream = assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charset.defaultCharset())
        } catch (ex: IOException) {
            ex.printStackTrace()
            ERROR
        }
    }

    //todo use GSON to parse JSON
    //todo replace GSON with Moshi to parse JSON
    private fun parseJson(json: String): List<Question> {
        if (json == ERROR || TextUtils.isEmpty(json)) {
            Log.e(TAG, "Error getting JSON from file!")
            return emptyList()
        }

        val arrayList = arrayListOf<Question>()

        val root = JSONArray(json)
        for (i in 0 until root.length()) {
            val element = root.getJSONObject(i)

            var text = element.getString("text")

            //if the sentence doesn't end in a period, add one
            if (text.takeLast(1) != ".") text += "."

            val keyed = element.getString("keyed")
            val domain = when (element.getString("domain")) {
                "O" -> "Openness"
                "C" -> "Conscientiousness"
                "E" -> "Extraversion"
                "A" -> "Agreeableness"
                "N" -> "Neuroticism"
                else -> ERROR
            }

            if (domain == ERROR) {
                Log.e(TAG, "Error detecting question domain!")
                continue
            }

            if (TextUtils.isEmpty(text) || TextUtils.isEmpty(keyed) || TextUtils.isEmpty(domain)) {
                Log.e(TAG, "Empty question found!")
                continue
            }

            arrayList.add(Question(i, text, keyed, domain))
        }

        //shuffle question order
        return arrayList.toMutableList().apply { shuffle() } as ArrayList<Question>
    }

    //when the user clicks a RadioButton, update the score
    private fun clickedRadio() {
        //activate and set up submit button if appropriate
        if (submit.visibility != View.VISIBLE && answeredAllQuestions()) {
            //scroll RecyclerView to bottom to match layout change
            recyclerView.scrollToPosition(questions.size - 1)

            //show submit button to move to next activity
            submit.visibility = View.VISIBLE

            //set OnClickListener to pass data to results activity
            submit.setOnClickListener {
                //calculate each of the dimensions based on the results
                val answerSummary = calculateResult()

                val intent = Intent(this, ResultsActivity::class.java).apply {
                    putExtra(JSON_FILE, filename)
                    putExtra(ANSWER_SUMMARY, answerSummary)
                }
                startActivity(intent)
            }
        }
    }

    private fun calculateResult(): HashMap<String, Int> {
        val map = hashMapOf(
                "Openness" to 0,
                "Conscientiousness" to 0,
                "Extraversion" to 0,
                "Agreeableness" to 0,
                "Neuroticism" to 0
        )

        questions.forEach { question ->
            val answer = if (question.keyed == "plus") question.answer else 5 - question.answer + 1
            val previousValue = map[question.domain] ?: -1

            if(previousValue == -1) Log.e(TAG, "Error calculating answer scores!")

            map[question.domain] = map[question.domain]?.plus(answer) ?: 0
        }

        return map
    }

    private fun answeredAllQuestions(): Boolean = questions.all { it.answer != 0 }
}
