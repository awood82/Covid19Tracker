package com.example.android.covid19tracker.screen_region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.android.covid19tracker.R
import com.example.android.covid19tracker.databinding.FragmentRegionBinding
import com.example.android.covid19tracker.util.RootFragment

class RegionFragment : RootFragment() {

    private val viewModel: RegionViewModel by lazy {
        ViewModelProviders.of(this).get(RegionViewModel::class.java)
    }

    private var viewModelAdapter: RegionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?) : View? {

        // NOTE: onCreateOptionsMenu and onOptionsItemSelected are implemented by the
        // parent RootFragment because all Fragments currently use this same code.
        setHasOptionsMenu(true)

        val binding = FragmentRegionBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        viewModelAdapter = RegionAdapter(RegionClickListener {
            viewModel.displayRegionalStats(it)
//            Toast.makeText(activity, "Clicked " + it.name, Toast.LENGTH_SHORT).show()
        })
        binding.root.findViewById<RecyclerView>(R.id.region_recycler).adapter = viewModelAdapter

        viewModel.navigateToBottomSheet.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                findNavController().navigate(RegionFragmentDirections.actionRegionFragmentToBottomSheetFragment(it))
                // Prevent multiple navigations in case the user rotates the screen
                viewModel.displayRegionalStatsComplete()
            }
        })

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