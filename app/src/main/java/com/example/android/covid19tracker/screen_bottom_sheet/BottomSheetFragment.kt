package com.example.android.covid19tracker.screen_bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.android.covid19tracker.databinding.FragmentBottomSheetBinding
import com.example.android.covid19tracker.domain.RegionalStats
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
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

        val listPieEntry = listOf(
            PieEntry(regionalStats.infectedCases.replace(",", "").toFloat()),
            PieEntry(regionalStats.recoveryCases.replace(",", "").toFloat()),
            PieEntry(regionalStats.deathCases.replace(",", "").toFloat())
        )
        val set = PieDataSet(listPieEntry, "")
        set.colors = listOf(
            binding.infectedCasesText.textColors.defaultColor,
            binding.recoveredCasesText.textColors.defaultColor,
            binding.deathCasesText.textColors.defaultColor)
        with (binding.pieChart) {
            setUsePercentValues(true)
            data = PieData(set)
            invalidate()
        }
/*
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(18.5f, "Green"));
        entries.add(new PieEntry(26.7f, "Yellow"));
        entries.add(new PieEntry(24.0f, "Red"));
        entries.add(new PieEntry(30.8f, "Blue"));
        PieDataSet set = new PieDataSet(entries, "Election Results");
        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
*/
        return binding.root
    }
}