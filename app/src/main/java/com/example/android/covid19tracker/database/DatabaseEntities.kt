package com.example.android.covid19tracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.covid19tracker.domain.RegionalStats

@Entity
data class DatabaseRegionalStats constructor(
    @PrimaryKey
    val name: String,
    val flagUrl: String,
    val totalCases: String,
    val infectedCases: String,
    val recoveryCases: String,
    val deathCases: String,
    val lastUpdate: String = "Never",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
)

fun List<DatabaseRegionalStats>.asDomainModel(): List<RegionalStats> {
    return map {
        RegionalStats(
            name = it.name,
            flagUrl = it.flagUrl,
            totalCases = it.totalCases,
            infectedCases = it.infectedCases,
            recoveryCases = it.recoveryCases,
            deathCases = it.deathCases,
            lastUpdate = it.lastUpdate,
            latitude = it.latitude,
            longitude = it.longitude
        )
    }
}