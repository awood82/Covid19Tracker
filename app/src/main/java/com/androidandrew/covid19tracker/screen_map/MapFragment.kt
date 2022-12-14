package com.androidandrew.covid19tracker.screen_map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.androidandrew.covid19tracker.R
import com.androidandrew.covid19tracker.databinding.FragmentMapBinding
import com.androidandrew.covid19tracker.util.RootFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : RootFragment(), OnMapReadyCallback {

    private val viewModel: MapViewModel by lazy {
        val activity = requireNotNull(activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, MapViewModel.Factory(activity.application))
            .get(MapViewModel::class.java)
    }

    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // NOTE: onCreateOptionsMenu and onOptionsItemSelected are implemented by the
        // parent RootFragment because all Fragments currently use this same code.
        setHasOptionsMenu(true)

        val binding = FragmentMapBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.navigateToBottomSheet.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { stats ->
                findNavController().navigate(com.androidandrew.covid19tracker.screen_map.MapFragmentDirections.actionMapFragmentToBottomSheetFragment(
                    stats))
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.regionalStats.observe(viewLifecycleOwner, Observer {
            if (::googleMap.isInitialized) {
                for (region in it) {
                    val latLng = LatLng(region.latitude, region.longitude)
                    googleMap?.addMarker(MarkerOptions().position(latLng).title(region.name))
                }
            }
        })
    }

    override fun onMapReady(map: GoogleMap?) {
        if (map != null) {
            googleMap = map
            googleMap.setOnMarkerClickListener { marker ->
                if (null != marker) {
                    viewModel.displayRegionalStats(marker.title)
                    true
                }
                false
            }
        }
    }
}