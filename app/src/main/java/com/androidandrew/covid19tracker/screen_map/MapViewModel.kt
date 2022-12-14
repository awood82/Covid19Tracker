package com.androidandrew.covid19tracker.screen_map

import android.app.Application
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidandrew.covid19tracker.domain.RegionalStats
import com.androidandrew.covid19tracker.network.CovidApi
import com.androidandrew.covid19tracker.network.asDomainModel
import com.androidandrew.covid19tracker.util.LiveDataEvent
import kotlinx.coroutines.*
import java.io.IOException

class MapViewModel(val app: Application) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _regionalStats = MutableLiveData<List<RegionalStats>>()
    val regionalStats: LiveData<List<RegionalStats>>
        get() = _regionalStats

    private val _navigateToBottomSheet = MutableLiveData<LiveDataEvent<RegionalStats>>()
    val navigateToBottomSheet: LiveData<LiveDataEvent<RegionalStats>>
        get() = _navigateToBottomSheet

    init {
        getRegionLocations()
    }

    private fun getRegionLocations() {
        coroutineScope.launch {
            try {
                val getRegionalInfoDeferred =
                    CovidApi.service.getRegionalStats("total_cases", "desc")
                val regionalInfoResult = getRegionalInfoDeferred.await()
                var stats = regionalInfoResult.asDomainModel()

                // Assumption: The "World" region is still returned in the list of countries
                // and it shouldn't be displayed.
                if (stats[0].name == "World") {
                    stats = stats.subList(1, stats.lastIndex + 1)
                }

                getLocationMarkers(stats)
                _regionalStats.value = stats
            } catch (e: Exception) {
                _regionalStats.value = ArrayList()
            }
        }
    }

    private suspend fun getLocationMarkers(stats: List<RegionalStats>) {
        withContext(Dispatchers.IO) {
            val geocoder = Geocoder(app)
            for (region in stats) {
                try {
                    val addressList = geocoder.getFromLocationName(region.name, 1)
                    if (addressList?.isNotEmpty()!!) {
                        val location = addressList[0]
                        with (region) {
                            latitude = location.latitude
                            longitude = location.longitude
                        }
                    }
                } catch (e: IOException) {
                    Log.e("Geocoder error", e.message ?: "")
                }
            }
        }
    }

    /**
     * Requests another screen to display COVID-19 stats about the country that was just clicked.
     */
    fun displayRegionalStats(name: String) {
        val region = findRegionByName(name)
        region?.let {
            _navigateToBottomSheet.value = LiveDataEvent(it)
        }
    }

    private fun findRegionByName(name: String): RegionalStats? {
        val regions = _regionalStats.value
        if (!regions.isNullOrEmpty()) {
            for (region in regions) {
                if (region.name == name) {
                    return region
                }
            }
        }
        return null
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Factory for constructing a specific ViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MapViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}