package com.example.fitnesss.screens.library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fitnesss.R
import com.example.fitnesss.databinding.FragmentLibraryBinding
import com.example.fitnesss.screens.library.LibraryFragmentViewModel_Factory.create
import com.example.fitnesss.screens.workouts.WorkoutsViewModel
import com.example.fitnesss.utils.viewModelCreator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LibraryFragment : Fragment(R.layout.fragment_library) {

    private var _binding: FragmentLibraryBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("FragmentLibraryBinding is null")

    private val viewModel by viewModels<LibraryFragmentViewModel>()

    private val adapter = initAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLibraryBinding.bind(view)
        setupView()
    }

    private fun setupView() {
        binding.rvLibrary.adapter = adapter
        lifecycleScope.launchWhenCreated {
            viewModel.data.collect {
                adapter.listLibrary = it
            }
        }
    }

    private fun initAdapter(): LibraryAdapter {
        return LibraryAdapter{

            val direction = LibraryFragmentDirections.actionLibraryFragmentToWorkoutsFragment(it.id)
            findNavController().navigate(direction)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}