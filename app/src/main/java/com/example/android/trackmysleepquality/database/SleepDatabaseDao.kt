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

package com.example.android.trackmysleepquality.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SleepDatabaseDao {

    @Insert (onConflict =OnConflictStrategy.REPLACE)
    fun insert(sleepNight: SleepNight)

    @Update
    fun update(sleepNight: SleepNight)

    @Query("SELECT * FROM Sleep where nightID=:key")
    fun get(key:Long):SleepNight?

    @Query("DELETE FROM Sleep")
    fun clear()

    @Query("SELECT * FROM Sleep ORDER BY nightID DESC")
    fun getAllNights():LiveData<List<SleepNight>>

    @Query("SELECT * FROM Sleep ORDER BY nightID DESC LIMIT 1")
    fun getTonight():SleepNight?
}
