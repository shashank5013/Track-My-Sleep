package com.example.android.trackmysleepquality.sleeptracker

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight


@BindingAdapter("sleepDuration")
fun TextView.setSleepDurationString(sleepNight: SleepNight?){
    sleepNight?.let {
        this.text= convertDurationToFormatted(it.startTime,it.endTime,resources)
    }
}

@BindingAdapter("sleepQuality")
fun TextView.setSleepQualityString(sleepNight: SleepNight?){
    sleepNight?.let {
        this.text= convertNumericQualityToString(it.sleepQuality,resources)
    }
}

@BindingAdapter("sleepQualityImage")
fun ImageView.setSleepQualityImage(sleepNight: SleepNight?){
    sleepNight?.let {
        this.setImageResource(
            when (it.sleepQuality) {
                0 -> R.drawable.ic_sleep_0
                1 -> R.drawable.ic_sleep_1
                2 -> R.drawable.ic_sleep_2
                3 -> R.drawable.ic_sleep_3
                4 -> R.drawable.ic_sleep_4
                5 -> R.drawable.ic_sleep_5
                else -> R.drawable.ic_sleep_active
            }
        )
    }
}

