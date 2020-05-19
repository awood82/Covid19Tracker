package com.example.android.covid19tracker.network

import android.content.res.Resources
import com.example.android.covid19tracker.R
import com.example.android.covid19tracker.domain.GeneralItemCard
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

fun NetworkGlobalContainer.asDomainModelCards(res: Resources): List<GeneralItemCard> {
    val cards = ArrayList<GeneralItemCard>()
    cards.add(
        GeneralItemCard(
            drawable = R.drawable.ic_description_24px,
            type = res.getString(R.string.total_confirmed_cases),
            cases = data.total_cases,
            color = res.getColor(R.color.colorTotalCases),
            lastUpdate = data.last_update
        )
    )
    cards.add(
        GeneralItemCard(
            drawable = R.drawable.ic_report_problem_24px,
            type = res.getString(R.string.currently_infected),
            cases = data.currently_infected,
            color = res.getColor(R.color.colorInfected),
            lastUpdate = data.last_update
        )
    )
    cards.add(
        GeneralItemCard(
            drawable = R.drawable.ic_favorite_24px,
            type = res.getString(R.string.recovered),
            cases = data.recovery_cases,
            color = res.getColor(R.color.colorRecovered),
            lastUpdate = data.last_update
        )
    )
    cards.add(
        GeneralItemCard(
            drawable = R.drawable.ic_face_24px,
            type = res.getString(R.string.deaths),
            cases = data.death_cases,
            color = res.getColor(R.color.colorDead),
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