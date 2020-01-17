package com.adriantache.bigfivepersonalitytest.chart

import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter

/**
 * Custom value formatter for bar chart value labels
 **/
class NumberValueFormatter : ValueFormatter() {
    override fun getBarLabel(barEntry: BarEntry?): String {
        return barEntry?.y?.toInt().toString()
    }
}