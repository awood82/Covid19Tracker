package com.androidandrew.covid19tracker.screen_region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.androidandrew.covid19tracker.R
import com.androidandrew.covid19tracker.database.StatsDatabase
import com.androidandrew.covid19tracker.databinding.FragmentRegionBinding
import com.androidandrew.covid19tracker.network.CovidApi
import com.androidandrew.covid19tracker.repository.StatsRepository
import com.androidandrew.covid19tracker.util.RootFragment

class RegionFragment : RootFragment() {

    private val viewModel: RegionViewModel by lazy {
        val activity = requireNotNull(activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val repo = StatsRepository(StatsDatabase.getInstance(activity.applicationContext).statsDatabaseDao, CovidApi.service)
        ViewModelProviders.of(this, RegionViewModel.Factory(repo))
            .get(RegionViewModel::class.java)
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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModelAdapter = RegionAdapter(RegionClickListener {
            viewModel.displayRegionalStats(it)
        })
        binding.root.findViewById<RecyclerView>(R.id.region_recycler).adapter = viewModelAdapter

        viewModel.navigateToBottomSheet.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { stats ->
                findNavController().navigate(com.androidandrew.covid19tracker.screen_region.RegionFragmentDirections.actionRegionFragmentToBottomSheetFragment(
                    stats))
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