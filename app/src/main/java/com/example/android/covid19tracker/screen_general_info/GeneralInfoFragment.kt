package com.example.android.covid19tracker.screen_general_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.android.covid19tracker.R
import com.example.android.covid19tracker.databinding.FragmentGeneralBinding
import com.example.android.covid19tracker.util.setupPieChart
import com.github.mikephil.charting.charts.PieChart

class GeneralInfoFragment : Fragment() {

    private val viewModel: GeneralInfoViewModel by lazy {
        val activity = requireNotNull(activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, GeneralInfoViewModel.Factory(activity.application))
            .get(GeneralInfoViewModel::class.java)
    }

    private var viewModelAdapter: GeneralItemAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?) : View? {

        val binding = FragmentGeneralBinding.inflate(inflater)

        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        viewModelAdapter = GeneralItemAdapter()
        binding.root.findViewById<RecyclerView>(R.id.general_recycler).adapter = viewModelAdapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.generalStats.observe(viewLifecycleOwner, Observer { stats ->
            val pieChart = requireView().findViewById<PieChart>(R.id.pie_chart)
            setupPieChart(stats, pieChart, resources)
            pieChart.description.text = "As of " + stats.lastUpdate
        })
        viewModel.generalItemCards.observe(viewLifecycleOwner, Observer { info ->
            info?.apply {
                viewModelAdapter?.infoStats = info
            }
        })
    }
}