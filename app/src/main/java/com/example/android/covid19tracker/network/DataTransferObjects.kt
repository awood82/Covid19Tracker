package com.example.android.covid19tracker.network

import com.example.android.covid19tracker.domain.GeneralStats
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkGeneralStats(
    val total_cases: String,
    val currently_infected: String,
    val recovery_cases: String,
    val death_cases: String,
    val last_update: String
)

data class NetworkGeneralContainer(
    val data: NetworkGeneralStats
)

fun NetworkGeneralContainer.asDomainModel(): GeneralStats {
    return GeneralStats(
        totalCases = data.total_cases,
        infectedCases = data.currently_infected,
        recoveryCases = data.recovery_cases,
        deathCases = data.death_cases,
        lastUpdate = data.last_update
    )
}