package com.example.fitnesss.screens.library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.example.fitnesss.R
import com.example.fitnesss.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment(R.layout.fragment_library) {

    private var _binding: FragmentLibraryBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("FragmentLibraryBinding is null")

    private val viewModel by viewModels<LibraryFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLibraryBinding.bind(view)
        setupView()
    }

    private fun setupView() {
        val adapter = LibraryAdapter()
        binding.rvLibrary.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { list ->
            adapter.listLibrary = list
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}