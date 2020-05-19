package com.example.android.covid19tracker.screen_general_info

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.covid19tracker.domain.GeneralItemCard
import com.example.android.covid19tracker.domain.GeneralStats
import com.example.android.covid19tracker.network.CovidApi
import com.example.android.covid19tracker.network.asDomainModel
import com.example.android.covid19tracker.network.asDomainModelCards
import com.example.android.covid19tracker.util.LoadingStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GeneralInfoViewModel(val app: Application) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _generalStats = MutableLiveData<GeneralStats>()
    val generalStats: LiveData<GeneralStats>
        get() = _generalStats

    private val _generalItemCards = MutableLiveData<List<GeneralItemCard>>()
    val generalItemCards: LiveData<List<GeneralItemCard>>
        get() = _generalItemCards

    private val _generalLoadingStatus = MutableLiveData<LoadingStatus>()
    val generalLoadingStatus: LiveData<LoadingStatus>
        get() = _generalLoadingStatus

    init {
        getGeneralItems()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun getGeneralItems() {
        _generalLoadingStatus.value = LoadingStatus.LOADING
        coroutineScope.launch {
            try {
                var getGeneralInfoDeferred = CovidApi.service.getGlobalStats()
                var generalInfoResult = getGeneralInfoDeferred.await()
                _generalStats.value = generalInfoResult.asDomainModel()
                _generalItemCards.value = generalInfoResult.asDomainModelCards(app.resources)
                _generalLoadingStatus.value = LoadingStatus.DONE
            } catch (e: Exception) {
                _generalLoadingStatus.value = LoadingStatus.ERROR
                _generalStats.value = GeneralStats()
                _generalItemCards.value = ArrayList()
            }
        }
    }

    /**
     * Factory for constructing a specific ViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GeneralInfoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return GeneralInfoViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}