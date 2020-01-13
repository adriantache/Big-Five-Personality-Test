package com.adriantache.bigfivepersonalitytest

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.adriantache.bigfivepersonalitytest.adapters.QuestionListAdapter
import com.adriantache.bigfivepersonalitytest.databinding.ActivityQuizBinding
import com.adriantache.bigfivepersonalitytest.db.AppDatabase
import com.adriantache.bigfivepersonalitytest.db.QuestionEntity.Companion.convertEntityToQuestion
import com.adriantache.bigfivepersonalitytest.models.Question
import com.adriantache.bigfivepersonalitytest.utils.ANSWER_SUMMARY
import com.adriantache.bigfivepersonalitytest.utils.JSON_FILE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


//todo implement facets? => complexity
class QuizActivity : AppCompatActivity(), QuestionListAdapter.Interaction, CoroutineScope {
    companion object {
        private val TAG = QuizActivity::class.java.simpleName
    }

    private var questions = mutableListOf<Question>()

    //selected question json filename
    private lateinit var filename: String

    //RecyclerView implementation
    private lateinit var questionsAdapter: QuestionListAdapter

    //DataBinding
    private lateinit var binding: ActivityQuizBinding

    //Coroutine Scope
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz)

        //init coroutine scope
        job = Job()

        //enable immersive mode
        launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                isImmersive = true
            }
        }

        //read JSON filename from intent
        filename = intent.getStringExtra(JSON_FILE) ?: ""

        if (TextUtils.isEmpty(filename)) {
            Log.e(TAG, "Error getting JSON filename!")
        }

        //RecyclerView implementation
        initRecyclerView()

        //create list of questions from the file
        launch { getQuestions() }

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
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@QuizActivity)
            questionsAdapter = QuestionListAdapter(this@QuizActivity).apply {
                setHasStableIds(true)
            }
            adapter = questionsAdapter
        }
    }

    private suspend fun getQuestions() {
        val questionDatabase = Room
                .databaseBuilder(applicationContext, AppDatabase::class.java, "database")
                .build()
        val questionDao = questionDatabase.questionDao()
        val entityList = questionDao.findBySet(filename)

        entityList.forEach { questionEntity ->
            questions.add(convertEntityToQuestion(questionEntity))
        }

        questionsAdapter.submitList(questions)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        //if available, enter immersive mode while in the Quiz activity
        if (hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Enables regular immersive mode.
            // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
            // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            launch {
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
        }
    }

    //RecyclerView onclick action
    override fun onItemSelected(position: Int, selection: Int) {
        //update answer in list
        questions[position].answer = selection

        //update list in the adapter
        questionsAdapter.notifyItemChanged(position)

        //trigger post-click checks
        clickedRadio()
    }

    //when the user clicks a RadioButton, update the score
    private fun clickedRadio() {
        //if submit button is already visible, do nothing
        if (binding.submit.visibility == View.VISIBLE) return

        //activate and set up submit button and progress bar if appropriate
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

    private fun answeredQuestions(): Int = questions.count { it.answer != 0 } * 100
}
