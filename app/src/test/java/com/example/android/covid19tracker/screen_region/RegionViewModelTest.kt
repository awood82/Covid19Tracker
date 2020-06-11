package com.example.android.covid19tracker.screen_region

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.covid19tracker.getOrAwaitValue
import com.example.android.covid19tracker.network.FakeCovid19Service
import com.example.android.covid19tracker.repository.FakeRepository
import com.example.android.covid19tracker.util.MainCoroutineScopeRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RegionViewModelTest {

    private val repository = FakeRepository()

    @Test
    fun getRegionalInfo_withOneCountry() = runBlockingTest {
        repository.setStats(listOf(FakeRepository.USA_STATS))
        val viewModel = RegionViewModel(repository)
//        viewModel.regionalStats.getOrAwaitValue() // Wait for the initial call to init{}

        viewModel.updateRegionalInfo()
        val results = viewModel.regionalStats.getOrAwaitValue()

        val countryNamesMap = results.map { it.name.toLowerCase() }
        assertTrue(countryNamesMap.contains("usa"))
    }

    @Test
    fun getRegionalInfo_withTwoCountries() = runBlockingTest {
        val viewModel = RegionViewModel(repository)
        repository.setStats(listOf(FakeRepository.USA_STATS, FakeRepository.GERMANY_STATS))

        viewModel.updateRegionalInfo()
        val results = viewModel.regionalStats.getOrAwaitValue()

        val countryNamesMap = results.map { it.name.toLowerCase() }
        assertTrue(countryNamesMap.contains("usa"))
        assertTrue(countryNamesMap.contains("germany"))
    }

    // NOTE: The ViewModel no longer contains the logic for removing the "World" country.
    // That is done in the real Repository.
    // TODO: Where should the logic for removing the "World" result from the network live?
    // Right now it's in the repository so that the ViewModel and Database don't need to filter.
    // This filters it from the server's response.
    @Test
    fun getRegionalInfo_withWorldInResult_leavesIt() = runBlockingTest {
        val viewModel = RegionViewModel(repository)
        repository.setStats(FakeRepository.SAMPLE_LIST) // SAMPLE_LIST contains "World"

        viewModel.updateRegionalInfo()
        val results = viewModel.regionalStats.getOrAwaitValue()

        val countryNamesMap = results.map { it.name.toLowerCase() }
        assertThat(countryNamesMap.contains("world"), `is`(true))
    }
}