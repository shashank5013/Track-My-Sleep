/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleepquality

import android.app.Application
import android.provider.SyncStateContract.Helpers.update
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SleepQualityViewModel(
        val database: SleepDatabaseDao,
        application: Application):AndroidViewModel(application){

        private val _qualityUpdateEnded=MutableLiveData<Boolean>()
        val qualityUpdateEnded: LiveData<Boolean>
        get()=_qualityUpdateEnded

        fun updateNight(quality:Int){
            viewModelScope.launch {
                val tonight=getTonight()
                tonight?.sleepQuality=quality
                update(tonight!!)
                _qualityUpdateEnded.value=true
            }
        }

        private suspend fun getTonight() = withContext(Dispatchers.IO){
            database.getTonight()
        }
        private suspend fun update(sleepNight: SleepNight)= withContext(Dispatchers.IO){
            database.update(sleepNight)
        }
        fun updateEnded(){
            _qualityUpdateEnded.value=false
        }

}
