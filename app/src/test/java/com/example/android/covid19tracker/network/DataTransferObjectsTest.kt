package com.example.android.covid19tracker.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test

class DataTransferObjectsTest {

//    private val sampleServerResponse = """{"data": {"total_cases": "6,480,521", "recovery_cases": "3,086,574", "death_cases": "383,042", "last_update": "Jun, 03 2020, 12:28, UTC", "currently_infected": "3,010,905", "cases_with_outcome": "3,469,616", "mild_condition_active_cases": "2,956,412", "critical_condition_active_cases": "54,493", "recovered_closed_cases": "3,086,574", "death_closed_cases": "383,042", "closed_cases_recovered_percentage": "89.0", "closed_cases_death_percentage": "11.0", "active_cases_mild_percentage": "98.0", "active_cases_critical_percentage": "2.0", "general_death_rate": "6.0"}, "status": "success"}"""
    private val defaultGlobalStats = NetworkGeneralStats("6,480,521", "3,010,905", "3,086,574", "383,042", "Jun, 03 2020, 12:28, UTC")
    private val defaultGlobalContainer = NetworkGlobalContainer(defaultGlobalStats)

//    private val moshi = Moshi.Builder()
//        .add(KotlinJsonAdapterFactory())
//        .build()

    @Test
    fun networkGlobalContainer_asDomainModel_toGeneralStats() {
        val stats = defaultGlobalContainer.asDomainModel()
//        val body = moshi.adapter(NetworkGlobalContainer::class.java).toJson(defaultGlobalContainer)

        assertThat(stats.totalCases, `is`(defaultGlobalStats.total_cases))
        assertThat(stats.recoveryCases, `is`(defaultGlobalStats.recovery_cases))
        assertThat(stats.deathCases, `is`(defaultGlobalStats.death_cases))
        assertThat(stats.infectedCases, `is`(defaultGlobalStats.currently_infected))
        assertThat(stats.lastUpdate, `is`(defaultGlobalStats.last_update))
    }
}