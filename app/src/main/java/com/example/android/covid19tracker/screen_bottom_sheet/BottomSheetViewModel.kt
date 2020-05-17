package com.example.android.covid19tracker.screen_bottom_sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.covid19tracker.domain.RegionalStats

class BottomSheetViewModel(regionalStats: RegionalStats) : ViewModel() {

    private val _regionalStats = MutableLiveData<RegionalStats>()
    val regionalStats: LiveData<RegionalStats>
        get() = _regionalStats

    init {
        _regionalStats.value = regionalStats
    }


    /**
     * Simple Factory to pass the regional stats to the bottom sheet.
     */
    class BottomSheetFactory(
        private val regionalStats: RegionalStats) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BottomSheetViewModel::class.java)) {
                return BottomSheetViewModel(regionalStats) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}