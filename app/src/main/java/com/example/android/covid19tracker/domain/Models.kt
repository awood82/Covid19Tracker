package com.example.android.covid19tracker.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class GeneralStats(
    val totalCases: String = "0",
    val infectedCases: String = "0",
    val recoveryCases: String = "0",
    val deathCases: String = "0",
    val lastUpdate: String = "Never"
)

data class GeneralItemCard(
    val drawable: Int,
    val type: Int,
    val cases: String = "",
    val lastUpdate: String = "",
    val color: Int
)

@Parcelize
data class RegionalStats(
    val name: String = "",
    val flagUrl: String = "",
    val totalCases: String = "0",
    val infectedCases: String = "0",
    val recoveryCases: String = "0",
    val deathCases: String = "0",
    val lastUpdate: String = "Never",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0) : Parcelable
