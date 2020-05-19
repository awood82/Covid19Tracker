package com.example.android.covid19tracker.util

import android.content.res.Resources
import com.example.android.covid19tracker.R
import com.example.android.covid19tracker.databinding.FragmentBottomSheetBinding
import com.example.android.covid19tracker.domain.RegionalStats
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

// Convert a number with commas to float
// e.g. "1,234,567" => 1234567.0
fun String.toFloatEx(): Float {
    return this.replace(",", "").toFloat()
}

// Setup a pie chart programmatically
fun setupPieChart(stats: RegionalStats, pieChart: PieChart, resources: Resources) {
    val set = PieDataSet(
        listOf(
            PieEntry(stats.infectedCases.toFloatEx()),
            PieEntry(stats.recoveryCases.toFloatEx()),
            PieEntry(stats.deathCases.toFloatEx())
        ), ""
    )
    set.colors = listOf(
        resources.getColor(R.color.colorInfected),
        resources.getColor(R.color.colorRecovered),
        resources.getColor(R.color.colorDead)
    )
    with (pieChart) {
        data = PieData(set)
        data.setValueTextSize(16.0f)
        // NOTE: If you don't pass in the 'this' parameter, the '%' signs will be drawn
        data.setValueFormatter(PercentFormatter(this))
        setUsePercentValues(true)
        // Hide the items at the bottom of the chart that are not useful
        legend.isEnabled = false
        description.text = ""
        // Trigger redraw
        invalidate()
    }
}