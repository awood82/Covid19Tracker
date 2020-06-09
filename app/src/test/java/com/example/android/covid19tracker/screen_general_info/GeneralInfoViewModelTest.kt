package com.example.android.covid19tracker.screen_general_info

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.covid19tracker.getOrAwaitValue
import com.example.android.covid19tracker.network.FakeCovid19Service
import com.example.android.covid19tracker.network.asDomainModel
import com.example.android.covid19tracker.util.LoadingStatus
import com.example.android.covid19tracker.util.MainCoroutineScopeRule
import com.example.android.covid19tracker.util.getValueForTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class GeneralInfoViewModelTest {

    private lateinit var viewModel: GeneralInfoViewModel
    private val service: FakeCovid19Service = FakeCovid19Service

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @After
    fun resetService() {
        service.reset()
    }

    @Test
    fun status_starting() {
        viewModel = GeneralInfoViewModel(service)

        assertThat(viewModel.loadingStatus.value, `is`(LoadingStatus.LOADING))
    }

    @Test
    fun status_loading() {
        viewModel = GeneralInfoViewModel(service)

        coroutineScope.advanceTimeBy(1L)

        assertThat(viewModel.loadingStatus.value, `is`(LoadingStatus.LOADING))
    }

    @Test
    fun status_error() {
        service.shouldReturnError = true
        viewModel = GeneralInfoViewModel(service)

//        viewModel.generalItemCards.getOrAwaitValue()

        assertThat(viewModel.loadingStatus.value, `is`(LoadingStatus.ERROR))
    }

    @Test
    fun status_done() {
        service.shouldReturnError = false
        viewModel = GeneralInfoViewModel(service)

        coroutineScope.advanceTimeBy(service.DEFAULT_DELAY * 2)

//        viewModel.generalItemCards.getOrAwaitValue()

        assertThat(viewModel.loadingStatus.getValueForTest(), `is`(LoadingStatus.DONE))
        val expectedStats = FakeCovid19Service.defaultGlobalContainer.asDomainModel()
        assertThat(viewModel.generalStats.getValueForTest(), `is`(expectedStats))
    }
}