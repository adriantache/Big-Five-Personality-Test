package com.adriantache.bigfivepersonalitytest

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.adriantache.bigfivepersonalitytest.databinding.ActivityResultsBinding
import com.adriantache.bigfivepersonalitytest.utils.ANSWER_SUMMARY
import com.adriantache.bigfivepersonalitytest.utils.JSON_FILE
import com.adriantache.bigfivepersonalitytest.viewmodel.ResultsViewModel
import com.adriantache.bigfivepersonalitytest.viewmodel.ResultsViewModelFactory
import com.jjoe64.graphview.ValueDependentColor
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import kotlin.math.abs


//todo add option to save screenshot (seems complicated)
//todo change charting library to MPAndroid
class ResultsActivity : AppCompatActivity() {
    companion object {
        private val TAG = ResultsActivity::class.java.simpleName
    }

    private lateinit var viewModel: ResultsViewModel

    //DataBinding
    private lateinit var binding: ActivityResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_results)

        //get filename and use it to specify test variant in the results text
        val filename = intent.getStringExtra(JSON_FILE)

        //if we cannot identify the filename, we quit this activity
        if (filename.isNullOrEmpty()) {
            Log.e(TAG, "Error getting JSON filename!")
            Toast.makeText(this, "Cannot identify question set!", Toast.LENGTH_SHORT).show()
            finish()
        }

        //get results HashMap from intent
        @Suppress("UNCHECKED_CAST")
        val resultsMap = intent.getSerializableExtra(ANSWER_SUMMARY) as HashMap<String, Int>

        if (resultsMap.isEmpty()) {
            Log.e(TAG, "Error getting results HashMap!")
            Toast.makeText(this, "Error getting test results!", Toast.LENGTH_LONG).show()
            finish()
        }

        val viewModelFactory = ResultsViewModelFactory(resultsMap, filename!!)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ResultsViewModel::class.java)

        binding.resultsViewModel = viewModel

        //set up the chart, using min and max values
        setUpGraph(viewModel.min, viewModel.max)

        binding.wikiButton.setOnClickListener { wikiClick() }
        binding.shareButton.setOnClickListener { shareClick() }
        binding.resetButton.setOnClickListener { resetClick() }
    }

    private fun setUpGraph(min: Double, max: Double) {
        //add values
        val series = BarGraphSeries<DataPoint>(arrayOf(
                DataPoint(0.0, viewModel.openness),
                DataPoint(1.0, viewModel.conscientiousness),
                DataPoint(2.0, viewModel.extraversion),
                DataPoint(3.0, viewModel.agreeableness),
                DataPoint(4.0, viewModel.neuroticism)
        ))

        binding.graph.addSeries(series)

        //set graph to more sensible Y bounds
        binding.graph.viewport.isYAxisBoundsManual = true
        binding.graph.viewport.setMinY(min - 1)
        binding.graph.viewport.setMaxY(max + 1)

        //set color of chart column based on value
        series.valueDependentColor = ValueDependentColor { data ->
            val percent: Double = abs((data.y - min) / (max - min))
            return@ValueDependentColor Color.rgb(50, (percent * 255).toInt(), 50)
        }

        //tweak some graphical graph stuff
        series.spacing = 5
        series.isDrawValuesOnTop = true
        series.valuesOnTopColor = Color.BLACK

        //set custom labels
        val staticLabelsFormatter = StaticLabelsFormatter(binding.graph)
        staticLabelsFormatter.setHorizontalLabels(arrayOf("O", "C", "E", "A", "N"))
        binding.graph.gridLabelRenderer.labelFormatter = staticLabelsFormatter
    }

    private fun wikiClick() {
        val webPage = Uri.parse("https://en.wikipedia.org/wiki/Big_Five_personality_traits")
        val intent = Intent(Intent.ACTION_VIEW, webPage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun shareClick() {
        val sharedText = "${viewModel.descriptionText} \n\n" +
                "${viewModel.summaryText.replace("\n", "")} \n\n" +
                "${viewModel.resultsText}  \n\n" +
                "https://en.wikipedia.org/wiki/Big_Five_personality_traits"

        //trigger intent to share the message
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Results for the Big Five Markers Test")
        intent.putExtra(Intent.EXTRA_TEXT, sharedText)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun resetClick() {
        val intent = Intent(this, MainActivity::class.java).setFlags(FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
