package com.example.android.covid19tracker.screen_map

import android.app.Application
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.covid19tracker.domain.RegionalStats
import com.example.android.covid19tracker.network.CovidApi
import com.example.android.covid19tracker.network.asDomainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MapViewModel(val app: Application) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _regionalStats = MutableLiveData<List<RegionalStats>>()
    val regionalStats: LiveData<List<RegionalStats>>
        get() = _regionalStats

    init {
        getRegionLocations()
    }

    private fun getRegionLocations() {
        coroutineScope.launch {
            var getRegionalInfoDeferred =
                CovidApi.service.getRegionalStats("total_cases", "desc")
            var regionalInfoResult = getRegionalInfoDeferred.await()
            val stats = regionalInfoResult.asDomainModel()
            val geocoder = Geocoder(app)
            for (region in stats) {
                val addressList = geocoder.getFromLocationName(region.name, 1)
                if (addressList.isNotEmpty()) {
                    val location = addressList.get(0)
                    with(region) {
                        latitude = location.latitude
                        longitude = location.longitude
                    }
                }
            }
            _regionalStats.value = stats
        }
    }

    /**
     * Factory for constructing a specific ViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MapViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}