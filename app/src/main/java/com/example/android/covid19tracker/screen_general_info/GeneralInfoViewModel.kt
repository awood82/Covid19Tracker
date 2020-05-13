package com.example.android.covid19tracker.screen_general_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.covid19tracker.domain.GeneralStats
import com.example.android.covid19tracker.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GeneralInfoViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _generalStats = MutableLiveData<String>()
    val generalStats: LiveData<String>
        get() = _generalStats

    init {
        getGeneralInfo()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun getGeneralInfo() {
        _generalStats.value = "Nothing downloaded yet" //GeneralStats("1", "2", "3", "4", "5")
        coroutineScope.launch {
            var getGeneralInfoDeferred = Network.covid19Service.getGeneralStats()
            var generalInfoResult = getGeneralInfoDeferred.await()
            _generalStats.value = generalInfoResult
        }
    }

}