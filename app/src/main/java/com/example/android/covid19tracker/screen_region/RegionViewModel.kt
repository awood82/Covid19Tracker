package com.example.android.covid19tracker.screen_region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.covid19tracker.domain.RegionalStats
import com.example.android.covid19tracker.network.Covid19Service
import com.example.android.covid19tracker.network.CovidApi
import com.example.android.covid19tracker.network.asDomainModel
import com.example.android.covid19tracker.screen_general_info.GeneralInfoViewModel
import com.example.android.covid19tracker.util.LoadingStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

open class RegionViewModel(val service: Covid19Service) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _regionalStats = MutableLiveData<List<RegionalStats>>()
    val regionalStats: LiveData<List<RegionalStats>>
        get() = _regionalStats

    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

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
    internal fun getRegionalInfo() {
        _loadingStatus.value = LoadingStatus.LOADING
        coroutineScope.launch {
            try {
                var getRegionalInfoDeferred =
                    service.getRegionalStats("total_cases", "desc")
                var regionalInfoResult = getRegionalInfoDeferred.await()
                var stats = regionalInfoResult.asDomainModel()

                // Assumptions: The "World" region is still returned first in the list of countries
                // and it shouldn't be displayed.
                if (stats.get(0).name == "World") {
                    stats = stats.subList(1, stats.lastIndex + 1)
                }
                _regionalStats.value = stats
                _loadingStatus.value = LoadingStatus.DONE
            } catch (e: Exception) {
                _regionalStats.value = ArrayList()
                _loadingStatus.value = LoadingStatus.ERROR
            }
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


    /**
     * Factory for constructing a specific ViewModel with parameter
     */
    class Factory(val service: Covid19Service) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegionViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RegionViewModel(service) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}