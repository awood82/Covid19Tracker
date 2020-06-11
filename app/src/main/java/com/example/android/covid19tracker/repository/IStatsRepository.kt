package com.example.android.covid19tracker.repository

import androidx.lifecycle.LiveData
import com.example.android.covid19tracker.domain.RegionalStats

interface IStatsRepository {
    //val regionalStats:
    fun getRegionalStats(): LiveData<List<RegionalStats>>

    suspend fun refreshRegionalStats()
}