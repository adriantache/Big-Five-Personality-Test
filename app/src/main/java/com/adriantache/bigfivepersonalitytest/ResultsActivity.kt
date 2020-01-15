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
import com.adriantache.bigfivepersonalitytest.utils.*
import com.adriantache.bigfivepersonalitytest.viewmodel.ResultsViewModel
import com.jjoe64.graphview.ValueDependentColor
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import kotlin.math.abs


//todo add option to save screenshot (seems complicated)
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

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(ResultsViewModel::class.java)

        //get filename and use it to specify test variant in the results text
        val filename = intent.getStringExtra(JSON_FILE)

        //if we cannot identify the filename, we quit this activity
        if (filename.isNullOrEmpty()) {
            Log.e(TAG, "Error getting JSON filename!")
            Toast.makeText(this, "Cannot identify question set!", Toast.LENGTH_SHORT).show()
            finish()
        }

        viewModel.setDescription(filename!!)

        binding.resultsText.text = viewModel.descriptionText

        //get results HashMap from intent
        @Suppress("UNCHECKED_CAST")
        val resultsMap = intent.getSerializableExtra(ANSWER_SUMMARY) as HashMap<String, Int>

        if (resultsMap.isEmpty()) Toast.makeText(this, "Error getting test results!", Toast.LENGTH_LONG).show()

        //sort HashMap by value to help with generating text
        viewModel.setList(resultsMap)

        //set up the chart, using min and max values
        setUpGraph(viewModel.min, viewModel.max)

        //set summary text
        binding.graphLegend.text = viewModel.summaryText

        //set descriptive text
        binding.resultsTextAuto.text = viewModel.resultsText

        binding.wikiButton.setOnClickListener { wikiClick() }
        binding.shareButton.setOnClickListener { shareClick() }
        binding.resetButton.setOnClickListener { resetClick() }
    }

    private fun setUpGraph(min: Double, max: Double) {
        //add values
        val series = BarGraphSeries<DataPoint>(arrayOf(
                DataPoint(0.0, viewModel.resultsMap["Openness"]?.toDouble() ?: -1.0),
                DataPoint(1.0, viewModel.resultsMap["Conscientiousness"]?.toDouble() ?: -1.0),
                DataPoint(2.0, viewModel.resultsMap["Extraversion"]?.toDouble() ?: -1.0),
                DataPoint(3.0, viewModel.resultsMap["Agreeableness"]?.toDouble() ?: -1.0),
                DataPoint(4.0, viewModel.resultsMap["Neuroticism"]?.toDouble() ?: -1.0)
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
