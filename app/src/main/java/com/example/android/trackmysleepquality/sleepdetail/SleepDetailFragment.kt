package com.example.android.trackmysleepquality.sleepdetail

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepDetailBinding


/**
 * A simple [Fragment] subclass.
 * Use the [SleepDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SleepDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding= FragmentSleepDetailBinding.inflate(layoutInflater,container,false)

        val args:SleepDetailFragmentArgs by navArgs()
        val application= requireNotNull(this.activity).application
        val database=SleepDatabase.getInstance(application).sleepDatabaseDao()
        val sleepDetailViewModelFactory=SleepDetailViewModelFactory(args.nightID,database,application)
        val sleepDetailViewModel=ViewModelProvider(this,sleepDetailViewModelFactory).get(SleepDetailViewModel::class.java)

        binding.sleepDetailViewModel=sleepDetailViewModel
        binding.lifecycleOwner = this

        binding.closeButton.setOnClickListener {
            findNavController().navigate(SleepDetailFragmentDirections.actionSleepDetailFragmentToSleepTrackerFragment())
        }
        return binding.root
    }

}