package com.example.fitnesss.screens.workouts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.fitnesss.R
import com.example.fitnesss.databinding.FragmentGainWeightBinding
import com.example.fitnesss.databinding.FragmentGoodShapeBinding
import com.example.fitnesss.screens.library.LibraryFragmentViewModel

class GoodShapeFragment: Fragment(R.layout.fragment_good_shape) {
    private var _binding: FragmentGoodShapeBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("FragmentLibraryBinding is null")

    private val viewModel by viewModels<LibraryFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGoodShapeBinding.bind(view)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}