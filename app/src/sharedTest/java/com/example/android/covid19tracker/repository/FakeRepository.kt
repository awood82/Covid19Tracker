package com.example.android.covid19tracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.covid19tracker.domain.RegionalStats

class FakeRepository :
    IStatsRepository {
    companion object {
        // Sample stats to use for testing
        val WORLD_STATS =
            RegionalStats(
                "World",
                "",
                "5,000",
                "2,500",
                "2,000",
                "500"
            )
        val USA_STATS = RegionalStats(
            "USA",
            "",
            "1,000",
            "500",
            "400",
            "100"
        )
        val GERMANY_STATS =
            RegionalStats(
                "Germany",
                "",
                "100",
                "50",
                "40",
                "10"
            )
        // The real server always returns worldStats first
        val SAMPLE_LIST = listOf(WORLD_STATS, USA_STATS, GERMANY_STATS)
    }

    private val regionalStats =
        MutableLiveData<List<RegionalStats>>()
    private var futureStats = mutableListOf<RegionalStats>()

    fun setStats(stats: List<RegionalStats>) {
        futureStats.addAll(stats)
    }

    // Real implementation reads data from the database
    override fun getRegionalStats(): LiveData<List<RegionalStats>> {
        return regionalStats
    }

    // Real implementation requests an update from the server
    override suspend fun refreshRegionalStats() {
        regionalStats.value = futureStats
    }

}