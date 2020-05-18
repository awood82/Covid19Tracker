package com.example.android.covid19tracker.screen_map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.covid19tracker.screen_region.RegionViewModel
import com.google.android.gms.maps.model.LatLng

class MapViewModel : RegionViewModel() {

    private val _locationList = MutableLiveData<List<LatLng>>()
    val locationList: LiveData<List<LatLng>>
        get() = _locationList
}