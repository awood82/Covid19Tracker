package com.androidandrew.covid19tracker.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import com.androidandrew.covid19tracker.R
import com.androidandrew.covid19tracker.domain.GeneralStats
import com.androidandrew.covid19tracker.domain.RegionalStats
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import java.io.FileOutputStream


enum class LoadingStatus { LOADING, ERROR, DONE }

// Pick a name for the screenshot file. It doesn't have to be unique because
// we don't need to save it for later. We'll just write over it for future screenshots.
val SCREENSHOT_FILENAME = "screenshot.jpg"

// Convert a number with commas to float
// e.g. "1,234,567" => 1234567.0
fun String.toFloatEx(): Float {
    try {
        return this.replace(",", "").toFloat()
    } catch (e: Exception) {
        return 0f
    }
}

// Setup a pie chart programmatically
fun setupPieChart(stats: GeneralStats, pieChart: PieChart, resources: Resources) {
    setupPieChart(stats.infectedCases, stats.recoveryCases, stats.deathCases, pieChart, resources)
}

// Setup a pie chart programmatically
fun setupPieChart(stats: RegionalStats, pieChart: PieChart, resources: Resources) {
    setupPieChart(stats.infectedCases, stats.recoveryCases, stats.deathCases, pieChart, resources)
}

private fun setupPieChart(infected: String, recovered: String, dead: String, pieChart: PieChart, resources: Resources) {
    val set = PieDataSet(
        listOf(
            PieEntry(infected.toFloatEx()),
            PieEntry(recovered.toFloatEx()),
            PieEntry(dead.toFloatEx())
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

fun takeScreenshot(view: View): Bitmap {
    view.isDrawingCacheEnabled = true
    view.buildDrawingCache(true)
    val bitmap = Bitmap.createBitmap(view.drawingCache)
    view.isDrawingCacheEnabled = false
    return bitmap
}

fun saveScreenshot(bitmap: Bitmap, activity: Activity): Uri {
    val path = activity.getFileStreamPath(SCREENSHOT_FILENAME)
    var out: FileOutputStream? = null

    try {
        out = activity.openFileOutput(SCREENSHOT_FILENAME, Context.MODE_PRIVATE)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        out.flush()
    } catch (e: Exception) {
        Toast.makeText(
            activity,
            activity.resources.getString(R.string.error, e.message),
            Toast.LENGTH_LONG
        ).show()
        Log.e("saveScreenshot", e.message ?: "")
    } finally {
        try {
            out?.close()
        } catch (e: Exception) {
        }
    }
    return FileProvider.getUriForFile(
        activity,
        "com.androidandrew.covid19tracker.provider", path
    )
}
