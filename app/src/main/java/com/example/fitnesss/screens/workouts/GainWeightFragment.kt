package com.example.fitnesss.screens.workouts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.fitnesss.R
import com.example.fitnesss.databinding.FragmentGainWeightBinding
import com.example.fitnesss.databinding.FragmentLibraryBinding
import com.example.fitnesss.screens.library.LibraryAdapter
import com.example.fitnesss.screens.library.LibraryFragmentViewModel

class GainWeightFragment : Fragment(R.layout.fragment_gain_weight) {
    private var _binding: FragmentGainWeightBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("FragmentLibraryBinding is null")

    private val viewModel by viewModels<LibraryFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGainWeightBinding.bind(view)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}