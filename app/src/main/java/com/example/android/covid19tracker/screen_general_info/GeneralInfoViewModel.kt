package com.example.android.covid19tracker.screen_general_info

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.covid19tracker.domain.GeneralItemCard
import com.example.android.covid19tracker.domain.GeneralStats
import com.example.android.covid19tracker.network.*
import com.example.android.covid19tracker.util.LoadingStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GeneralInfoViewModel(val service: Covid19Service) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _generalStats = MutableLiveData<GeneralStats>()
    val generalStats: LiveData<GeneralStats>
        get() = _generalStats

    private val _generalItemCards = MutableLiveData<List<GeneralItemCard>>()
    val generalItemCards: LiveData<List<GeneralItemCard>>
        get() = _generalItemCards

    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    init {
        getGeneralItems()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    @VisibleForTesting
    internal fun getGeneralItems() {
        _loadingStatus.value = LoadingStatus.LOADING
        coroutineScope.launch {
            try {
                var getGeneralInfoDeferred = service.getGlobalStats()
                var generalInfoResult = getGeneralInfoDeferred.await()
                // Set the loading status first so we can unit test it later.
                // Since the status is set multiple times, it is harder to reliabily test.
                _loadingStatus.value = LoadingStatus.DONE
                _generalItemCards.value = generalInfoResult.asDomainModelCards()
                _generalStats.value = generalInfoResult.asDomainModel()
            } catch (e: Exception) {
                _loadingStatus.value = LoadingStatus.ERROR
                _generalItemCards.value = ArrayList()
                _generalStats.value = GeneralStats()
            }
        }
    }

    /**
     * Factory for constructing a specific ViewModel with parameter
     */
    class Factory(val service: Covid19Service) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GeneralInfoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return GeneralInfoViewModel(service) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}