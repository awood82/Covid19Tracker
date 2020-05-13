package com.example.android.covid19tracker.domain

/*
{"data": {"total_cases": "4,390,141", "recovery_cases": "1,633,621", "death_cases": "295,323", "last_update": "May, 13 2020, 16:28, UTC", "currently_infected": "2,461,197", "cases_with_outcome": "1,928,944", "mild_condition_active_cases": "2,415,010", "critical_condition_active_cases": "46,187", "recovered_closed_cases": "1,633,621", "death_closed_cases": "295,323", "closed_cases_recovered_percentage": "85.0", "closed_cases_death_percentage": "15.0", "active_cases_mild_percentage": "98.0", "active_cases_critical_percentage": "2.0", "general_death_rate": "7.000000000000001"}, "status": "success"}
 */

data class GeneralStats(
    val totalCases: Long,
    val infectedCases: Long,
    val recoveryCases: Long,
    val deathCases: Long,
    val lastUpdated: String
)