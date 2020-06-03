package com.example.android.covid19tracker.screen_general_info

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.covid19tracker.getOrAwaitValue
import com.example.android.covid19tracker.network.FakeCovid19Service
import com.example.android.covid19tracker.network.NetworkGlobalContainer
import com.example.android.covid19tracker.network.asDomainModel
import com.example.android.covid19tracker.util.LoadingStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class GeneralInfoViewModelTest {

    private lateinit var viewModel: GeneralInfoViewModel
    private val service: FakeCovid19Service = FakeCovid19Service

    @After
    fun resetService() {
        service.reset()
    }

    @Test
    fun status_loading() = runBlockingTest {
        service.delay = 2000L
        viewModel = GeneralInfoViewModel(service)

        assertThat(viewModel.loadingStatus.value, `is`(LoadingStatus.LOADING))
    }

    @Test
    fun status_error() = runBlockingTest {
        service.shouldReturnError = true
        viewModel = GeneralInfoViewModel(service)

        viewModel.generalItemCards.getOrAwaitValue()

        assertThat(viewModel.loadingStatus.value, `is`(LoadingStatus.ERROR))
    }

    @Test
    fun status_done() = runBlockingTest {
        service.shouldReturnError = false
        viewModel = GeneralInfoViewModel(service)

        viewModel.generalItemCards.getOrAwaitValue()

        assertThat(viewModel.loadingStatus.value, `is`(LoadingStatus.DONE))
        val expectedStats = FakeCovid19Service.defaultGlobalContainer.asDomainModel()
        assertThat(viewModel.generalStats.value, `is`(expectedStats))
    }
}