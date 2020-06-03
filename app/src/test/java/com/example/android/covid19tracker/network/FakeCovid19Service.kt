package com.example.android.covid19tracker.network

import com.example.android.covid19tracker.toDeferred
import kotlinx.coroutines.Deferred

object FakeCovid19Service: Covid19Service {

    var shouldReturnError = false
    var delay: Long = 0L

    private val globalStats = NetworkGeneralStats("100", "40", "50", "10", "N/A")
    val defaultGlobalContainer = NetworkGlobalContainer(globalStats)

    private val worldStats = NetworkRegionalStats("World", "", "5,000", "2,500", "2,000", "500")
    private val usaStats = NetworkRegionalStats("USA", "", "1,000", "500", "400", "100")
    private val germanyStats = NetworkRegionalStats("Germany", "", "100", "50", "40", "10")
    private val regionalStats = listOf(worldStats, usaStats, germanyStats) // The real server always returns worldStats first
    val defaultRegionalContainer = NetworkRegionalContainer(NetworkRegionalCountries(regionalStats))

    fun reset() {
        shouldReturnError = false
        delay = 0L
    }

    override fun getGlobalStats(): Deferred<NetworkGlobalContainer> {
        if (shouldReturnError) {
            throw(Exception())
        }
        return defaultGlobalContainer.toDeferred(delay)
    }

    override fun getRegionalStats(
        orderByField: String,
        orderByHow: String
    ): Deferred<NetworkRegionalContainer> {
        if (shouldReturnError) {
            throw(Exception())
        }
        return defaultRegionalContainer.toDeferred(delay)
    }

}