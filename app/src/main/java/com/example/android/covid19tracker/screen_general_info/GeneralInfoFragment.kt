package com.example.android.covid19tracker.screen_general_info

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.android.covid19tracker.R
import com.example.android.covid19tracker.databinding.FragmentGeneralBinding
import com.example.android.covid19tracker.network.CovidApi
import com.example.android.covid19tracker.util.*
import com.github.mikephil.charting.charts.PieChart

class GeneralInfoFragment : RootFragment() {

    private val viewModel: GeneralInfoViewModel by lazy {
        val activity = requireNotNull(activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, GeneralInfoViewModel.Factory(CovidApi.service))
            .get(GeneralInfoViewModel::class.java)
    }

    private var viewModelAdapter: GeneralItemAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?) : View? {

        // NOTE: onCreateOptionsMenu and onOptionsItemSelected are implemented by the
        // parent RootFragment because all Fragments currently use this same code.
        setHasOptionsMenu(true)

        val binding = FragmentGeneralBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner
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
            pieChart.description.text = resources.getString(R.string.last_update, stats.lastUpdate)
            pieChart.description.textSize = resources.getInteger(R.integer.piechart_text_size).toFloat()
        })
        viewModel.generalItemCards.observe(viewLifecycleOwner, Observer { info ->
            info?.apply {
                viewModelAdapter?.infoStats = info
            }
        })
    }
}