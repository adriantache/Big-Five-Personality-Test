package com.adriantache.bigfivepersonalitytest

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.adriantache.bigfivepersonalitytest.databinding.ActivityResultsBinding
import com.jjoe64.graphview.ValueDependentColor
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import kotlin.math.abs


//todo add option to save screenshot (seems complicated)
class ResultsActivity : AppCompatActivity() {
    private lateinit var resultsMap: HashMap<String, Int>
    private lateinit var summaryText: String
    private lateinit var resultsText: String
    private lateinit var descriptionText: String

    //DataBinding
    private lateinit var binding: ActivityResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_results)

        //get filename and use it to specify test variant in the results text
        val filename = intent.getStringExtra(JSON_FILE)
        descriptionText = "Thank you for completing the " +
                when (filename) {
                    "mini_ipip.json" -> "20 item Mini-IPIP"
                    "ipip50.json" -> "50 item IPIP-NEO-PI-R"
                    "neo_pi_r50.json" -> "50 item NEO-PI-R"
                    "neo_pi_r100.json" -> "100 item NEO-PI-R"
                    "deyoung_quilty_peterson100.json" -> "DeYoung, Quilty and Peterson 100-item NEO-PI-R"
                    "johnson120.json" -> "Johnson 120 item IPIP-NEO-PI-R"
                    "maples120.json" -> "Maples 120 item IPIP NEO-PI-R"
                    "costa_mccrae300.json" -> "Costa and McCrae 300 item IPIP-NEO-PI-R"
                    else -> ERROR
                } + " version of the Big Five Markers personality test. You can find additional details " +
                "about this test by going to ipip.ori.org or visiting the Wikipedia entry using the button " +
                "below. Please note that the results of this test are not saved, so we recommend taking a " +
                "screenshot or using the share button at the top to save them to your desired destination."
        binding.resultsText.text = descriptionText

        //get results HashMap from intent
        @Suppress("UNCHECKED_CAST")
        resultsMap = intent.getSerializableExtra(ANSWER_SUMMARY) as HashMap<String, Int>

        if (resultsMap.isEmpty()) Toast.makeText(this, "Error getting results!", Toast.LENGTH_LONG).show()

        //sort HashMap by value to help with generating text
        val sortedList = resultsMap.toList().sortedByDescending { it.second }

        //set up the chart, using min and max values
        val min = sortedList.component5().second.toDouble()
        val max = sortedList.component1().second.toDouble()
        setUpGraph(min, max)

        //generate and set summary text
        generateSummaryText(sortedList)

        //generate and set descriptive text
        generateResultsText(sortedList)

        binding.wikiButton.setOnClickListener { wikiClick() }
        binding.shareButton.setOnClickListener { shareClick() }
        binding.resetButton.setOnClickListener { resetClick() }
    }

    private fun setUpGraph(min: Double, max: Double) {
        //add values
        val series = BarGraphSeries<DataPoint>(arrayOf(
                DataPoint(0.0, resultsMap["Openness"]?.toDouble() ?: -1.0),
                DataPoint(1.0, resultsMap["Conscientiousness"]?.toDouble() ?: -1.0),
                DataPoint(2.0, resultsMap["Extraversion"]?.toDouble() ?: -1.0),
                DataPoint(3.0, resultsMap["Agreeableness"]?.toDouble() ?: -1.0),
                DataPoint(4.0, resultsMap["Neuroticism"]?.toDouble() ?: -1.0)
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

    private fun generateSummaryText(sortedList: List<Pair<String, Int>>) {
        summaryText = ""

        for (i in sortedList.indices) {
            val element = sortedList[i]

            summaryText += "${element.first}: ${element.second}  "

            if (i == 2) summaryText += "\n"
        }

        binding.graphLegend.text = summaryText
    }

    //todo there's probably a smarter way to build this text
    //todo find solution to equality between 3 and 4
    private fun generateResultsText(sortedList: List<Pair<String, Int>>) {
        val key = mapOf(
                "Extraversion" to ("inventive or curious" to "consistent or cautious"),
                "Agreeableness" to ("efficient or organized" to "easy-going or careless"),
                "Conscientiousness" to ("outgoing or energetic" to "solitary or reserved"),
                "Openness" to ("friendly or compassionate" to "challenging or detached"),
                "Neuroticism" to ("sensitive or nervous" to "secure or confident")
        )

        val firstTrait = sortedList.component1().first
        val secondTrait = sortedList.component2().first
        val thirdTrait = sortedList.component3().first
        val fourthTrait = sortedList.component4().first
        val fifthTrait = sortedList.component5().first

        //select two or three main traits, in case of equality
        val equality = (sortedList.component1().second == sortedList.component2().second ||
                sortedList.component2().second == sortedList.component3().second)

        resultsText = "It seems that $firstTrait"
        resultsText += if (equality) ", $secondTrait and $thirdTrait"
        else " and $secondTrait"
        resultsText += " are your primary traits. This means that you are probably more "
        resultsText += key[firstTrait]?.first ?: ERROR
        resultsText += if (equality) ", ${key[secondTrait]?.first
                ?: ERROR} and ${key[thirdTrait]?.first ?: ERROR}"
        else " and ${key[secondTrait]?.first ?: ERROR}"
        resultsText += " as well as more "
        resultsText += if (equality) "${key[fourthTrait]?.first
                ?: ERROR} and ${key[fifthTrait]?.first ?: ERROR}."
        else "${key[thirdTrait]?.first ?: ERROR}, ${key[fourthTrait]?.first
                ?: ERROR} and ${key[fifthTrait]?.first ?: ERROR}"

        binding.resultsTextAuto.text = resultsText
    }

    private fun wikiClick() {
        val webPage = Uri.parse("https://en.wikipedia.org/wiki/Big_Five_personality_traits")
        val intent = Intent(Intent.ACTION_VIEW, webPage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun shareClick() {
        val sharedText = "$descriptionText \n\n " +
                "${summaryText.replace("\n", "")} \n\n " +
                "$resultsText  \n\n" +
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
