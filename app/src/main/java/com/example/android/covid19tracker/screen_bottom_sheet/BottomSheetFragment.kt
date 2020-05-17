package com.example.android.covid19tracker.screen_bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.android.covid19tracker.databinding.FragmentBottomSheetBinding
import com.example.android.covid19tracker.domain.RegionalStats

class BottomSheetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBottomSheetBinding.inflate(inflater)

        binding.setLifecycleOwner(this)

        val regionalStats: RegionalStats = BottomSheetFragmentArgs.fromBundle(requireArguments()).regionalStats
        val viewModelFactory = BottomSheetViewModel.BottomSheetFactory(regionalStats)
        binding.viewModel = ViewModelProviders.of(this, viewModelFactory).get(BottomSheetViewModel::class.java)

        return binding.root
    }
}