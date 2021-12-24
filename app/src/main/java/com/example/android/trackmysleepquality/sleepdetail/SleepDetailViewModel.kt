package com.example.android.trackmysleepquality.sleepdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SleepDetailViewModel(
    private val nightID:Long,
    private val database:SleepDatabaseDao,
    application: Application
) : AndroidViewModel(application){

    private val _currNight= MutableLiveData<SleepNight?>()
    val currNight:LiveData<SleepNight?>
    get() = _currNight

    init {
        viewModelScope.launch {
            _currNight.value=getNight()
        }
    }

    private suspend fun getNight()= withContext(Dispatchers.IO){
        database.get(nightID)
    }
}