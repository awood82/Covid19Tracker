package com.example.android.covid19tracker.network

import android.content.res.Resources
import androidx.lifecycle.Transformations.map
import com.example.android.covid19tracker.R
import com.example.android.covid19tracker.domain.GeneralItemCard
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

fun NetworkGlobalContainer.asDomainModelCards(): List<GeneralItemCard> {
    val cards = ArrayList<GeneralItemCard>()
    cards.add(
        GeneralItemCard(
            drawable = R.drawable.ic_description_24px,
            type = R.string.total_confirmed_cases,
            cases = data.total_cases,
            color = R.color.colorTotalCases,
            lastUpdate = data.last_update
        )
    )
    cards.add(
        GeneralItemCard(
            drawable = R.drawable.ic_report_problem_24px,
            type = R.string.currently_infected,
            cases = data.currently_infected,
            color = R.color.colorInfected,
            lastUpdate = data.last_update
        )
    )
    cards.add(
        GeneralItemCard(
            drawable = R.drawable.ic_favorite_24px,
            type = R.string.recovered,
            cases = data.recovery_cases,
            color = R.color.colorRecovered,
            lastUpdate = data.last_update
        )
    )
    cards.add(
        GeneralItemCard(
            drawable = R.drawable.ic_face_24px,
            type = R.string.deaths,
            cases = data.death_cases,
            color = R.color.colorDead,
            lastUpdate = data.last_update
        )
    )
    return cards
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
            name = it.country,
            flagUrl = it.flag,
            totalCases = it.total_cases,
            infectedCases = it.active_cases,
            recoveryCases = it.total_recovered,
            deathCases = it.total_deaths
        )
    }
}