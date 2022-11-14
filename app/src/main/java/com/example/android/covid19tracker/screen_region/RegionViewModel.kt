package com.example.android.covid19tracker.screen_region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.covid19tracker.domain.RegionalStats
import com.example.android.covid19tracker.repository.IStatsRepository
import com.example.android.covid19tracker.util.LiveDataEvent
import com.example.android.covid19tracker.util.LoadingStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

open class RegionViewModel(internal val repository: IStatsRepository) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    private val _navigateToBottomSheet = MutableLiveData<LiveDataEvent<RegionalStats>>()
    val navigateToBottomSheet: LiveData<LiveDataEvent<RegionalStats>>
        get() = _navigateToBottomSheet

    val regionalStats = repository.getRegionalStats()

    private var alreadyLoaded = false

    init {
        if (!alreadyLoaded) {
            updateRegionalInfo()
            alreadyLoaded = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Downloads COVID-19 stats for all individual countries and sorts them
     * by the total number of cases, starting with the country with the most cases.
     */
    internal fun updateRegionalInfo() {
        _loadingStatus.value = LoadingStatus.LOADING
        coroutineScope.launch {
            try {
                repository.refreshRegionalStats()
                _loadingStatus.value = LoadingStatus.DONE
            } catch (e: Exception) {
                _loadingStatus.value = LoadingStatus.ERROR
            }
        }
    }

    /**
     * Requests another screen to display COVID-19 stats about the country that was just clicked.
     */
    fun displayRegionalStats(region: RegionalStats) {
        _navigateToBottomSheet.value = LiveDataEvent(region)
    }


    /**
     * Factory for constructing a specific ViewModel with parameter
     */
    class Factory(private val repository: IStatsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegionViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RegionViewModel(repository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}