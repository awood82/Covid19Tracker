package com.example.android.covid19tracker.screen_region

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.covid19tracker.getOrAwaitValue
import com.example.android.covid19tracker.network.FakeCovid19Service
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RegionViewModelTest {

    private val service: FakeCovid19Service = FakeCovid19Service
    private val regionalResultsSize = service.defaultRegionalContainer.data.rows.size

    @After
    fun resetService() {
        service.reset()
    }

    @Test
    fun getRegionalInfo_defaultTestResultHasTwoCountries() = runBlockingTest {
        val viewModel = RegionViewModel(service)

        viewModel.getRegionalInfo()
        val results = viewModel.regionalStats.getOrAwaitValue()

        val countryNamesMap = results.map { it.name.toLowerCase() }
        assertTrue(countryNamesMap.contains("usa"))
        assertTrue(countryNamesMap.contains("germany"))
    }

    @Test
    fun getRegionalInfo_removesWorldFromResults() = runBlockingTest {
        service.shouldReturnError = false
        val viewModel = RegionViewModel(service)

        viewModel.getRegionalInfo()
        val results = viewModel.regionalStats.getOrAwaitValue()

        assertThat(results?.size, `is`(regionalResultsSize - 1))
        val countryNamesMap = results.map { it.name.toLowerCase() }
        assertThat(countryNamesMap.contains("world"), `is`(false))
    }
}