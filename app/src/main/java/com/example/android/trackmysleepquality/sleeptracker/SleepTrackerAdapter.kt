package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

class SleepTrackerAdapter : RecyclerView.Adapter<SleepTrackerAdapter.ViewHolder>() {

    class ViewHolder private constructor(val binding:ListItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(
            currNight: SleepNight
        ) {
            binding.sleepNight=currNight
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) =
                ViewHolder(
                    ListItemSleepNightBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
        }
    }

    private val callback=object : DiffUtil.ItemCallback<SleepNight>(){
        override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem.nightID==newItem.nightID
        }

        override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem==newItem
        }
    }

    val differ=AsyncListDiffer<SleepNight>(this,callback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currNight=differ.currentList[position]
        holder.bind(currNight)
    }


    override fun getItemCount()=differ.currentList.size



}