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

class RegionViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _regionalStats = MutableLiveData<List<RegionalStats>>()
    val regionalStats: LiveData<List<RegionalStats>>
        get() = _regionalStats

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
            _regionalStats.value = regionalInfoResult.asDomainModel()
        }
    }

    /**
     * Displays COVID-19 stats about the country that was just clicked.
     */
    fun displayRegionalStats(region: RegionalStats) {
        //Toast.makeText()
    }
}