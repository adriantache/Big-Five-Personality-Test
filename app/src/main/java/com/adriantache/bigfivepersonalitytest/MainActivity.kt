package com.adriantache.bigfivepersonalitytest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

const val ERROR = "ERROR"
const val JSON_FILE = "JSON_FILE"

class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set listeners for each button
        mini20.setOnClickListener(this)
        ipip50.setOnClickListener(this)
        neo50.setOnClickListener(this)
        neo100.setOnClickListener(this)
        dqp100.setOnClickListener(this)
        johnson120.setOnClickListener(this)
        maples120.setOnClickListener(this)
        costamccrae300.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val filename: String = when (v?.id) {
            R.id.mini20 -> "mini_ipip.json"
            R.id.ipip50 -> "ipip50.json"
            R.id.neo50 -> "neo_pi_r50.json"
            R.id.neo100 -> "neo_pi_r100.json"
            R.id.dqp100 -> "deyoung_quilty_peterson100.json"
            R.id.johnson120 -> "johnson120.json"
            R.id.maples120 -> "maples120.json"
            R.id.costamccrae300 -> "costa_mccrae300.json"
            else -> ERROR
        }

        //if we have an error, means we had a problem with the when above
        if (filename == ERROR) {
            Log.e(TAG, "Error identifying view!")
            return
        }

        //open the QuizActivity and pass the name of the JSON file to use to generate the quiz
        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra(JSON_FILE, filename)
        }
        startActivity(intent)
    }
}
