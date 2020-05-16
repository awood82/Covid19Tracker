package com.example.android.covid19tracker.domain

import android.graphics.Color

data class GeneralStats(
    val totalCases: String = "0",
    val infectedCases: String = "0",
    val recoveryCases: String = "0",
    val deathCases: String = "0",
    val lastUpdate: String = "Never"
)

data class GeneralItemCard(
    val drawable: Int,
    val type: String = "",
    val cases: String = "",
    val lastUpdate: String = "",
    val color: Int
)

data class RegionalStats(
    val name: String = "",
    val flagUrl: String = "",
    val totalCases: String = "0",
    val infectedCases: String = "0",
    val recoveryCases: String = "0",
    val deathCases: String = "0",
    val lastUpdate: String = "Never"
)