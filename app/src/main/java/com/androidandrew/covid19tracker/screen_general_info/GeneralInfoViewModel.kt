package com.androidandrew.covid19tracker.screen_general_info

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.androidandrew.covid19tracker.domain.GeneralItemCard
import com.androidandrew.covid19tracker.domain.GeneralStats
import com.androidandrew.covid19tracker.network.Covid19Service
import com.androidandrew.covid19tracker.network.asDomainModel
import com.androidandrew.covid19tracker.network.asDomainModelCards
import com.androidandrew.covid19tracker.network.*
import com.androidandrew.covid19tracker.util.LoadingStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GeneralInfoViewModel(val service: Covid19Service) : ViewModel() {

    private val viewModelJob = Job()
//    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

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
        //coroutineScope.launch {
        viewModelScope.launch {
            try {
                var getGeneralInfoDeferred = service.getGlobalStats()
                var generalInfoResult = getGeneralInfoDeferred.await()
                // Set the loading status first so we can unit test it later.
                // Since the status is set multiple times, it is harder to reliabily test.
                _loadingStatus.postValue(LoadingStatus.DONE)
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
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GeneralInfoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return GeneralInfoViewModel(service) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}