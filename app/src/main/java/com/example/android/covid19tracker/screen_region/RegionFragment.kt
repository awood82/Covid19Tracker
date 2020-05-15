package com.example.android.covid19tracker.screen_region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.covid19tracker.R
import com.example.android.covid19tracker.databinding.FragmentRegionBinding
import com.example.android.covid19tracker.domain.RegionalStats

class RegionFragment : Fragment() {

    private val viewModel: RegionViewModel by lazy {
        ViewModelProviders.of(this).get(RegionViewModel::class.java)
    }

    private var viewModelAdapter: RegionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?) : View? {

        val binding = FragmentRegionBinding.inflate(inflater)

        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        viewModelAdapter = RegionAdapter()
        binding.root.findViewById<RecyclerView>(R.id.region_recycler).adapter = viewModelAdapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.regionalStats.observe(viewLifecycleOwner, Observer { stats ->
            stats?.apply {
                viewModelAdapter?.countryStats = stats
            }
        })
    }
}