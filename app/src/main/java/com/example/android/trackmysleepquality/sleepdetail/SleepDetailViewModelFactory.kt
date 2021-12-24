package com.example.android.trackmysleepquality.sleepdetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.sleepquality.SleepQualityViewModel

class SleepDetailViewModelFactory(
    private val nightID:Long,
    private val database:SleepDatabaseDao,
    private val application: Application
):ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepDetailViewModel::class.java)) {
            return SleepDetailViewModel(nightID, database,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}