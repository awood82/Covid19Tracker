package com.example.android.covid19tracker.screen_region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.android.covid19tracker.databinding.FragmentRegionBinding

class RegionFragment : Fragment() {

    private val viewModel: RegionViewModel by lazy {
        ViewModelProviders.of(this).get(RegionViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?) : View? {

        val binding = FragmentRegionBinding.inflate(inflater)

        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        return binding.root
    }
}