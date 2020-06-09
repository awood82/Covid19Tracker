package com.example.android.covid19tracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.covid19tracker.database.StatsDatabase
import com.example.android.covid19tracker.database.asDomainModel
import com.example.android.covid19tracker.domain.RegionalStats
import com.example.android.covid19tracker.network.Covid19Service
import com.example.android.covid19tracker.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StatsRepository(private val database: StatsDatabase, private val service: Covid19Service) {

    //val regionalStats:
    fun getRegionalStats(): LiveData<List<RegionalStats>> = Transformations.map(database.statsDatabaseDao.getRegionalStats()) {
        it.asDomainModel()
    }

    suspend fun refreshRegionalStats() {
        withContext(Dispatchers.IO) {
            val stats = service.getRegionalStats(
                "total_cases", "desc").await()
            // Assumptions: The "World" region is still returned first in the list of countries
            // and it shouldn't be displayed.
            var dbStats = stats.asDatabaseModel()
            if (dbStats.get(0).name == "World") {
                dbStats = dbStats.subList(1, dbStats.lastIndex + 1)
            }
            database.statsDatabaseDao.insertAll(dbStats)
        }
    }
}