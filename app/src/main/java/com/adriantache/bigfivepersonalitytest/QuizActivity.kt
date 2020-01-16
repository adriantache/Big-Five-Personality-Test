package com.adriantache.bigfivepersonalitytest

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adriantache.bigfivepersonalitytest.adapters.QuestionListAdapter
import com.adriantache.bigfivepersonalitytest.databinding.ActivityQuizBinding
import com.adriantache.bigfivepersonalitytest.models.Question
import com.adriantache.bigfivepersonalitytest.utils.ANSWER_SUMMARY
import com.adriantache.bigfivepersonalitytest.utils.JSON_FILE
import com.adriantache.bigfivepersonalitytest.viewmodel.QuizViewModel
import com.adriantache.bigfivepersonalitytest.viewmodel.QuizViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


//todo implement facets? => complexity
class QuizActivity : AppCompatActivity(), CoroutineScope, QuestionListAdapter.Interaction {
    companion object {
        private val TAG = QuizActivity::class.java.simpleName
    }

    private lateinit var viewModel: QuizViewModel

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

        job = Job()

        //enable immersive mode
        launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                isImmersive = true
            }
        }

        //read JSON filename from intent
        filename = intent.getStringExtra(JSON_FILE) ?: ""

        //if we cannot identify the filename, we quit this activity
        if (filename.isNullOrEmpty()) {
            Log.e(TAG, "Error getting JSON filename!")
            Toast.makeText(this, "Cannot identify question set!", Toast.LENGTH_SHORT).show()
            finish()
        }

        val viewModelFactory = QuizViewModelFactory(filename, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(QuizViewModel::class.java)

        viewModel.questions.observe(this, Observer<MutableList<Question>> {
            questionsAdapter.submitList(it)

            //if submit button is already visible, don't update UI
            if (binding.submit.visibility == View.VISIBLE) return@Observer

            if (viewModel.answeredAllQuestions()) {
                //scroll RecyclerView to bottom to match layout change
                binding.recyclerView.scrollToPosition(it.size - 1)

                //hide progressBar
                binding.progressBar.visibility = View.GONE

                //show submit button to move to next activity
                binding.submit.visibility = View.VISIBLE
            } else if (it.size != 0) {
                binding.progressBar.progress = (viewModel.answeredQuestions() * 100 / it.size).toInt()
            }
        })

        //set submit button OnClickListener to pass data to results activity
        binding.submit.setOnClickListener {
            //calculate each of the dimensions based on the results
            val answerSummary = viewModel.calculateResult()

            val intent = Intent(this, ResultsActivity::class.java).apply {
                putExtra(JSON_FILE, filename)
                putExtra(ANSWER_SUMMARY, answerSummary)
            }

            startActivity(intent)
        }

        //RecyclerView implementation
        initRecyclerView()
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
        viewModel.updateAnswer(position, selection)

        //update list in the adapter
        questionsAdapter.notifyItemChanged(position)
    }
}
