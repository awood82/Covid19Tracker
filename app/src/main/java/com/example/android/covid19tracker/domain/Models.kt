package com.example.android.covid19tracker.domain

data class GeneralStats(
    val totalCases: String = "0",
    val infectedCases: String = "0",
    val recoveryCases: String = "0",
    val deathCases: String = "0",
    val lastUpdate: String = "Never"
)