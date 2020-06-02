package com.example.android.covid19tracker.network

import com.example.android.covid19tracker.toDeferred
import kotlinx.coroutines.Deferred

object FakeCovid19Service: Covid19Service {

    var shouldReturnError = false
    var delay: Long = 0L

    private val defaultGlobalStats = NetworkGeneralStats("100", "40", "50", "10", "N/A")
    val defaultStats = NetworkGlobalContainer(defaultGlobalStats).asDomainModel()

    fun reset() {
        shouldReturnError = false
        delay = 0L
    }

    override fun getGlobalStats(): Deferred<NetworkGlobalContainer> {
        if (shouldReturnError) {
            throw(Exception())
        }
        val container = NetworkGlobalContainer(defaultGlobalStats)
        return container.toDeferred(delay)
    }

    override fun getRegionalStats(
        orderByField: String,
        orderByHow: String
    ): Deferred<NetworkRegionalContainer> {
        TODO()
    }

}