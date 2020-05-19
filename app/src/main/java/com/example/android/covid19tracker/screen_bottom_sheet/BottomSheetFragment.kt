package com.example.android.covid19tracker.screen_bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.android.covid19tracker.databinding.FragmentBottomSheetBinding
import com.example.android.covid19tracker.domain.RegionalStats
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBottomSheetBinding.inflate(inflater)

        binding.setLifecycleOwner(this)

        val regionalStats: RegionalStats = BottomSheetFragmentArgs.fromBundle(requireArguments()).regionalStats
        val viewModelFactory = BottomSheetViewModel.BottomSheetFactory(regionalStats)
        binding.viewModel = ViewModelProviders.of(this, viewModelFactory).get(BottomSheetViewModel::class.java)

        setupPieChart(regionalStats, binding)

        return binding.root
    }

    // Setup the pie chart programmatically
    private fun setupPieChart(stats: RegionalStats, binding: FragmentBottomSheetBinding) {
        val set = PieDataSet(listOf(
            PieEntry(stats.infectedCases.toFloatEx()),
            PieEntry(stats.recoveryCases.toFloatEx()),
            PieEntry(stats.deathCases.toFloatEx())
        ), "")
        set.colors = listOf(
            binding.infectedCasesText.textColors.defaultColor,
            binding.recoveredCasesText.textColors.defaultColor,
            binding.deathCasesText.textColors.defaultColor)
        with (binding.pieChart) {
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

    // Convert a number with commas to float
    // e.g. "1,234,567" => 1234567.0
    fun String.toFloatEx() : Float {
        return this.replace(",", "").toFloat()
    }
}