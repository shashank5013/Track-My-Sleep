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

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.*
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
        val database: SleepDatabaseDao,
        application: Application) : AndroidViewModel(application) {

        private val  _allNight=database.getAllNights()
        val allNight:LiveData<List<SleepNight>>
        get() = _allNight

        val allNightString=Transformations.map(allNight){
                        formatNights(it,application.resources)
        }

        private val _tonight=MutableLiveData<SleepNight?>()
        val tonight:LiveData<SleepNight?>
        get() = _tonight

        private val _currentNightEnded=MutableLiveData<Boolean>()
        val currentNightEnded:LiveData<Boolean>
        get() = _currentNightEnded

        val startButtonVisible=Transformations.map(_tonight){
                it==null
        }
        val stopButtonVisible=Transformations.map(_tonight){
                it!=null
        }


        init {
                _currentNightEnded.value=false
                initialiseTonight()
        }

        private fun initialiseTonight(){
                viewModelScope.launch {
                        val currentNight: SleepNight? = getTonight()
                        if(currentNight!=null && (currentNight.startTime==currentNight.endTime)){
                                _tonight.value=currentNight
                        }
                        else{
                                _tonight.value=null
                        }
                }
        }

        fun startTracking(){
                val currentNight=SleepNight()
                viewModelScope.launch{
                        insert(currentNight)
                        _tonight.value=getTonight()
                }

        }
        fun stopTracking(){
                var currentNight:SleepNight?
                viewModelScope.launch {
                        currentNight=getTonight()
                        currentNight?.let {
                                it.endTime=System.currentTimeMillis()
                                update(it)
                        }

                        _currentNightEnded.value=true

                }
        }
        fun clearAllNight(){
                viewModelScope.launch {
                        clear()
                }
        }

        fun nightEnded(){
                _currentNightEnded.value=false
        }

        private suspend fun insert(sleepNight: SleepNight)=withContext(Dispatchers.IO){
                database.insert(sleepNight)
        }
        private suspend fun update(sleepNight: SleepNight)=withContext(Dispatchers.IO){
                database.update(sleepNight)
        }
        private suspend fun clear()= withContext(Dispatchers.IO){
                database.clear()
        }
        private suspend fun getTonight()= withContext(Dispatchers.IO){
                database.getTonight()
        }

}



