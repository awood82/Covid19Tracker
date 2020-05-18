package com.example.android.covid19tracker.screen_region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.covid19tracker.domain.RegionalStats
import com.example.android.covid19tracker.network.CovidApi
import com.example.android.covid19tracker.network.asDomainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class RegionViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _regionalStats = MutableLiveData<List<RegionalStats>>()
    val regionalStats: LiveData<List<RegionalStats>>
        get() = _regionalStats

    private val _navigateToBottomSheet = MutableLiveData<RegionalStats>()
    val navigateToBottomSheet: LiveData<RegionalStats>
        get() = _navigateToBottomSheet

    init {
        getRegionalInfo()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Downloads COVID-19 stats for all individual countries and sorts them
     * by the total number of cases, starting with the country with the most cases.
     */
    private fun getRegionalInfo() {
        coroutineScope.launch {
            var getRegionalInfoDeferred =
                CovidApi.service.getRegionalStats("total_cases", "desc")
            var regionalInfoResult = getRegionalInfoDeferred.await()
            var stats = regionalInfoResult.asDomainModel()

            // Assumption: The "World" region is still returned in the list of countries
            // and it shouldn't be displayed.
            if (stats.get(0).name == "World") {
                stats = stats.subList(1, stats.lastIndex + 1)
            }
            _regionalStats.value = stats
        }
    }

    /**
     * Requests another screen to display COVID-19 stats about the country that was just clicked.
     */
    fun displayRegionalStats(region: RegionalStats) {
        _navigateToBottomSheet.value = region
    }

    fun displayRegionalStatsComplete() {
        _navigateToBottomSheet.value = null
    }
}