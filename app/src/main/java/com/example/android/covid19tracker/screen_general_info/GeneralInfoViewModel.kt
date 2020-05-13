package com.example.android.covid19tracker.screen_general_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.covid19tracker.domain.GeneralStats

class GeneralInfoViewModel : ViewModel() {

    //private val viewModelJob = Job()
    //private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _generalStats = MutableLiveData<GeneralStats>()
    val generalStats: LiveData<GeneralStats>
        get() = _generalStats

    private val _dummyText = MutableLiveData<String>()
    val dummyText: LiveData<String>
        get() = _dummyText

    init {
        getGeneralInfo()
    }

    override fun onCleared() {
        super.onCleared()
//        viewModelJob.cancel()
    }

    private fun getGeneralInfo() {
        _dummyText.value = "dummy text updated"
        _generalStats.value = GeneralStats("1", "2", "3", "4", "5")
        /*coroutineScope.launch {
            var getGeneralInfoDeferred = Network.covid19Service.getGeneralStats()
            var generalInfoResult = getGeneralInfoDeferred.await()
            _generalStats.value = generalInfoResult
        }*/
    }

}