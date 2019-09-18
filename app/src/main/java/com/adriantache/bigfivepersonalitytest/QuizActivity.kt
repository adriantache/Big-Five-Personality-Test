package com.adriantache.bigfivepersonalitytest

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_quiz.*
import org.json.JSONArray
import java.io.IOException
import java.nio.charset.Charset

const val ANSWER_SUMMARY = "ANSWER_SUMMARY"

//todo implement RecyclerView instead of just laying out all the questions
//todo implement immersion mode
//todo display questions in random order
class QuizActivity : AppCompatActivity() {
    companion object {
        private val TAG = QuizActivity::class.java.simpleName

        //helper to convert dp value to pixels
        val Int.px: Int
            get() = (this * Resources.getSystem().displayMetrics.density).toInt()
    }

    //holds all questions as a regular array
    private lateinit var questions: List<Question>

    //holds all answers as Ints
    private lateinit var answers: IntArray

    private lateinit var filename: String

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
            createRadioButtons(questions)

            //also create the answers array which automatically sets all values to 0
            answers = IntArray(questions.size)
        } else {
            Log.e(TAG, "Error getting Question array!")
            finish()
        }
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

            val text = element.getString("text")
            val keyed = element.getString("keyed")
            val domain = when (element.getString("domain")) {
                "O" -> "Openness"
                "N" -> "Neuroticism"
                "E" -> "Extraversion"
                "C" -> "Conscientiousness"
                "A" -> "Agreeableness"
                else -> ERROR
            }

            if (domain == ERROR) return emptyList()

            arrayList.add(Question(text, keyed, domain))
        }

        return arrayList
    }

    private fun createRadioButtons(questions: List<Question>) {
        questions.forEachIndexed { index, question ->
            //create RelativeLayout for each question
            val relativeLayout = RelativeLayout(this)
            relativeLayout.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)

            //create RadioGroup of choices
            val radioGroup = RadioGroup(this)
            radioGroup.id = index + 1000
            radioGroup.orientation = RadioGroup.HORIZONTAL
            radioGroup.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER
            radioGroup.setOnCheckedChangeListener { group, checkedId -> clickedRadio(group, checkedId) }

            //create each RadioButton in the group
            for (j in 0..4) {
                val radioButton = RadioButton(this)
                radioButton.gravity = Gravity.CENTER
                radioButton.id = j + 100
                radioGroup.addView(radioButton)
            }

            //set layout parameters to position RadioGroup in RelativeLayout
            val radioGroupParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT)
            radioGroupParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            radioGroupParams.addRule(RelativeLayout.CENTER_IN_PARENT)
            radioGroupParams.addRule(RelativeLayout.LEFT_OF, radioGroup.id)
            relativeLayout.addView(radioGroup, radioGroupParams)

            //make question text TextView
            val questionTextView = TextView(this)
            val text = question.text + "."
            questionTextView.text = text
            questionTextView.gravity = Gravity.CENTER_VERTICAL or Gravity.START
            questionTextView.setPadding(0, 10.px, 10.px, 10.px)

            //set layout parameters to position TextView in RelativeLayout
            val tVParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT)
            tVParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            tVParams.addRule(RelativeLayout.CENTER_IN_PARENT)
            tVParams.addRule(RelativeLayout.LEFT_OF, radioGroup.id)
            relativeLayout.addView(questionTextView, tVParams)

            //then add the whole RelativeLayout to the layout
            questionLayout.addView(relativeLayout)
        }
    }

    //when the user clicks a RadioButton, update the score
    private fun clickedRadio(group: RadioGroup, checkedId: Int) {
        val questionIndex = group.id - 1000
        val answerValue = checkedId - 100 + 1

        val question = questions[questionIndex]

        answers[questionIndex] = if (question.keyed == "plus") answerValue else 5 - answerValue + 1

        //activate and set up submit button if appropriate
        if (submit.visibility != View.VISIBLE && answeredAllQuestions()) {
            //scroll ScrollView to bottom to match layout change
            scrollView.post { scrollView.fullScroll(View.FOCUS_DOWN) }

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
        val map = hashMapOf("Extraversion" to 0,
                "Agreeableness" to 0,
                "Conscientiousness" to 0,
                "Openness" to 0,
                "Neuroticism" to 0
        )

        for (i in answers.indices) {
            map[questions[i].domain] = map[questions[i].domain]!!.plus(answers[i])
        }

        return map
    }

    private fun answeredAllQuestions(): Boolean = answers.all { it != 0 }
}
