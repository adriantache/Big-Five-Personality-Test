package com.adriantache.bigfivepersonalitytest

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.adriantache.bigfivepersonalitytest.chart.NumberValueFormatter
import com.adriantache.bigfivepersonalitytest.databinding.ActivityResultsBinding
import com.adriantache.bigfivepersonalitytest.utils.ANSWER_SUMMARY
import com.adriantache.bigfivepersonalitytest.utils.JSON_FILE
import com.adriantache.bigfivepersonalitytest.viewmodel.ResultsViewModel
import com.adriantache.bigfivepersonalitytest.viewmodel.ResultsViewModelFactory
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


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
        setUpGraph()

        binding.wikiButton.setOnClickListener { wikiClick() }
        binding.shareButton.setOnClickListener { shareClick() }
        binding.resetButton.setOnClickListener { resetClick() }
    }

    private fun setUpGraph() {
        //set up separate data sets for each dimension
        val o = listOf(BarEntry(0f, viewModel.openness))
        val oDS = BarDataSet(o, "Openness")
        oDS.setColor(0x002535, 0xff)
        val c = listOf(BarEntry(1f, viewModel.conscientiousness))
        val cDS = BarDataSet(c, "Conscientiousness")
        cDS.setColor(0x003851, 0xff)
        val e = listOf(BarEntry(2f, viewModel.extraversion))
        val eDS = BarDataSet(e, "Extraversion")
        eDS.setColor(0x22646e, 0xff)
        val a = listOf(BarEntry(3f, viewModel.agreeableness))
        val aDS = BarDataSet(a, "Agreeableness")
        aDS.setColor(0xbfd2d9, 0xff)
        val n = listOf(BarEntry(4f, viewModel.neuroticism))
        val nDS = BarDataSet(n, "Neuroticism")
        nDS.setColor(0x3b89ac, 0xff)

        //bring all sets together
        val barData = BarData(oDS, cDS, eDS, aDS, nDS)

        //format numbers and text size for data labels
        barData.setValueFormatter(NumberValueFormatter())
        barData.setValueTextSize(16f)
        barData.setValueTypeface(Typeface.DEFAULT)

        //disable description, grid bg and touch interaction
        binding.graph.description = Description().apply { text = "" }
        binding.graph.setDrawGridBackground(false)
        binding.graph.setTouchEnabled(false)

        //set axis bounds and disable axis drawing
        val xAxis = binding.graph.xAxis
        xAxis.mAxisMinimum = 0f
        xAxis.mAxisMaximum = 4f
        xAxis.isEnabled = false
        val yAxisRight = binding.graph.axisRight
        yAxisRight.isEnabled = false
        val yAxisLeft = binding.graph.axisLeft
        yAxisLeft.axisMinimum = viewModel.getChartMin()
        yAxisLeft.axisMaximum = viewModel.getChartMax()
        yAxisLeft.isEnabled = false

        //customize legend to hide colours
        val legend = binding.graph.legend
        legend.form = Legend.LegendForm.NONE
        legend.formSize = 0f

        //set chart data and refresh chart
        binding.graph.data = barData
        binding.graph.invalidate()
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
                "${viewModel.getSummaryText()} \n\n" +
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
