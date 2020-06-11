package com.example.android.covid19tracker.repository

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.covid19tracker.database.StatsDatabase
import com.example.android.covid19tracker.database.StatsDatabaseDao
import com.example.android.covid19tracker.getOrAwaitValue
import com.example.android.covid19tracker.network.FakeService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class StatsRepositoryTest {
    // Class under test
    private lateinit var statsRepository: StatsRepository

    private lateinit var db: StatsDatabase
    private lateinit var dao: StatsDatabaseDao
    private lateinit var service: FakeService

    @Before
    fun createRepository() {
        createDb()
        service = FakeService
        statsRepository = StatsRepository(dao, service)
    }

    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, StatsDatabase::class.java)
            // Allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()
        dao = db.statsDatabaseDao
    }

    @Test
    fun blankRepository_isEmpty() = runBlockingTest {
        assertThat(statsRepository.getRegionalStats().getOrAwaitValue().size, `is`(0))
    }

    @Test
    fun refreshRegionalStats_whenSuccessful_insertsTwoEntries() = runBlockingTest {
        statsRepository.refreshRegionalStats()

        val stats = statsRepository.getRegionalStats().getOrAwaitValue()

        assertThat(stats.size, `is`(2))
    }
}