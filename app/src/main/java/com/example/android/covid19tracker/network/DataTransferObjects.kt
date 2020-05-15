package com.example.android.covid19tracker.network

import com.example.android.covid19tracker.domain.GeneralStats
import com.example.android.covid19tracker.domain.RegionalStats
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkGeneralStats(
    val total_cases: String,
    val currently_infected: String,
    val recovery_cases: String,
    val death_cases: String,
    val last_update: String
)

@JsonClass(generateAdapter = true)
data class NetworkGlobalContainer(
    val data: NetworkGeneralStats
)

fun NetworkGlobalContainer.asDomainModel(): GeneralStats {
    return GeneralStats(
        totalCases = data.total_cases,
        infectedCases = data.currently_infected,
        recoveryCases = data.recovery_cases,
        deathCases = data.death_cases,
        lastUpdate = data.last_update
    )
}

@JsonClass(generateAdapter = true)
data class NetworkRegionalStats(
    val country: String,
    val flag: String,
    val total_cases: String,
    val active_cases: String,
    val total_recovered: String,
    val total_deaths: String
)

@JsonClass(generateAdapter = true)
data class NetworkRegionalContainer(
    val data: NetworkRegionalCountries
)

@JsonClass(generateAdapter = true)
data class NetworkRegionalCountries(
    val rows: List<NetworkRegionalStats>
)

fun NetworkRegionalContainer.asDomainModel(): List<RegionalStats> {
    return data.rows.map {
        RegionalStats(
            flagUrl = it.flag,
            totalCases = it.total_cases,
            infectedCases = it.active_cases,
            recoveryCases = it.total_recovered,
            deathCases = it.total_deaths
        )
    }
}