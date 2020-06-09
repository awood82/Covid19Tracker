package com.example.android.covid19tracker.database

import org.hamcrest.Matchers.`is`
import org.junit.Assert.*
import org.junit.Test

class DatabaseEntitiesTest {

    private val dbStat1 = DatabaseRegionalStats("country1", "url1", "100", "30", "20", "50", "N/A", 0.0, 0.0)
    private val dbStat2 = DatabaseRegionalStats("country2", "url2", "100", "30", "20", "50", "N/A", 0.0, 0.0)
    val dbStats = listOf(dbStat1, dbStat2)

    @Test
    fun databaseEntities_asDomainModel_toRegionalStats_withOneCountry() {
        val stats = listOf(dbStat1)

        val result = stats.asDomainModel()

        assertThat(result.size, `is`(1))
        assertThat(result.get(0).name, `is`("country1"))
    }

    @Test
    fun databaseEntities_asDomainModel_toRegionalStats_withTwoCountries() {
        val stats = listOf(dbStat1, dbStat2)

        val result = stats.asDomainModel()

        assertThat(result.size, `is`(2))
        assertThat(result.get(0).name, `is`("country1"))
        assertThat(result.get(1).name, `is`("country2"))
    }

    @Test
    fun databaseEntities_asDomainModel_toRegionalStats_withNoCountries() {
        val stats = ArrayList<DatabaseRegionalStats>()

        val result = stats.asDomainModel()

        assertThat(result.size, `is`(0))
    }

    /*@Test
    fun networkGlobalContainer_asDomainModel_toGeneralStats() {
        val stats = defaultGlobalContainer.asDomainModel()
//        val body = moshi.adapter(NetworkGlobalContainer::class.java).toJson(defaultGlobalContainer)

        Assert.assertThat(stats.totalCases, CoreMatchers.`is`(defaultGlobalStats.total_cases))
        Assert.assertThat(stats.recoveryCases, CoreMatchers.`is`(defaultGlobalStats.recovery_cases))
        Assert.assertThat(stats.deathCases, CoreMatchers.`is`(defaultGlobalStats.death_cases))
        Assert.assertThat(
            stats.infectedCases,
            CoreMatchers.`is`(defaultGlobalStats.currently_infected)
        )
        Assert.assertThat(stats.lastUpdate, CoreMatchers.`is`(defaultGlobalStats.last_update))
    }*/
}