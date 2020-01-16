package com.adriantache.bigfivepersonalitytest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.adriantache.bigfivepersonalitytest.databinding.ActivityMainBinding
import com.adriantache.bigfivepersonalitytest.utils.*

//todo implement landscape layout

class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    //DataBinding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //set listeners for each button
        binding.mini20.setOnClickListener(this)
        binding.ipip50.setOnClickListener(this)
        binding.neo50.setOnClickListener(this)
        binding.neo100.setOnClickListener(this)
        binding.dqp100.setOnClickListener(this)
        binding.johnson120.setOnClickListener(this)
        binding.maples120.setOnClickListener(this)
        binding.costamccrae300.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val filename: String = when (v?.id) {
            R.id.mini20 -> IPIP_20
            R.id.ipip50 -> IPIP_50
            R.id.neo50 -> NEO_50
            R.id.neo100 -> NEO_100
            R.id.dqp100 -> DYP_100
            R.id.johnson120 -> JOHN_120
            R.id.maples120 -> MAPLES_120
            R.id.costamccrae300 -> CM_300
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
