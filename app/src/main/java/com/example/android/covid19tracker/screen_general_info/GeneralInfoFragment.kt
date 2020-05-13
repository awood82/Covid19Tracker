package com.example.android.covid19tracker.screen_general_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.android.covid19tracker.databinding.FragmentGeneralInfoBinding

class GeneralInfoFragment : Fragment() {

    private val viewModel: GeneralInfoViewModel by lazy {
        ViewModelProviders.of(this).get(GeneralInfoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?) : View? {

        val binding = FragmentGeneralInfoBinding.inflate(inflater)

        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        return binding.root
    }
}