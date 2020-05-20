package com.example.android.covid19tracker.screen_general_info

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.android.covid19tracker.R
import com.example.android.covid19tracker.databinding.FragmentGeneralBinding
import com.example.android.covid19tracker.util.*
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

        setHasOptionsMenu(true)

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
            pieChart.description.text = resources.getString(R.string.last_update, stats.lastUpdate)
            pieChart.description.textSize = resources.getInteger(R.integer.piechart_text_size).toFloat()
        })
        viewModel.generalItemCards.observe(viewLifecycleOwner, Observer { info ->
            info?.apply {
                viewModelAdapter?.infoStats = info
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareScreenshot()
            R.id.about -> {
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun shareScreenshot() {
        val bitmap = ScreenshotUtil.takeScreenshot(requireView().rootView)
        val uri = ScreenshotUtil.saveScreenshot(bitmap, requireActivity())
        startActivity(getShareIntent(uri))
    }

    private fun getShareIntent(uri: Uri) : Intent {
        return ShareCompat.IntentBuilder.from(requireActivity())
            .setStream(uri)
            .setType("image/jpeg")
            .intent
    }
}