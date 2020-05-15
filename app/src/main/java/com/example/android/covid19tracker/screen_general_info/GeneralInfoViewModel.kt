package com.example.android.covid19tracker.screen_general_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.covid19tracker.domain.GeneralStats
import com.example.android.covid19tracker.network.CovidApi
import com.example.android.covid19tracker.network.asDomainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GeneralInfoViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _generalStats = MutableLiveData<GeneralStats>()
    val generalStats: LiveData<GeneralStats>
        get() = _generalStats

    init {
        getGeneralInfo()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun getGeneralInfo() {
        _generalStats.value = GeneralStats()
        coroutineScope.launch {
            var getGeneralInfoDeferred = CovidApi.service.getGlobalStats()
            var generalInfoResult = getGeneralInfoDeferred.await()
            _generalStats.value = generalInfoResult.asDomainModel()
        }
    }

}