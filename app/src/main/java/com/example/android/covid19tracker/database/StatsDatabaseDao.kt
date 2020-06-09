package com.example.android.covid19tracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StatsDatabaseDao {
    @Query("SELECT * FROM DatabaseRegionalStats ORDER BY totalCases DESC")
    fun getRegionalStats(): LiveData<List<DatabaseRegionalStats>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(stats: List<DatabaseRegionalStats>)
}