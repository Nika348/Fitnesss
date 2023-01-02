package com.example.fitnesss.screens.workouts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fitnesss.R
import com.example.fitnesss.databinding.FragmentWorkoutsBinding
import com.example.fitnesss.utils.viewModelCreator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WorkoutsFragment : Fragment(R.layout.fragment_workouts) {

    private var _binding: FragmentWorkoutsBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("FragmentWorkoutsBinding is null")

    private val args by navArgs<WorkoutsFragmentArgs>()

    @Inject
    lateinit var factory: WorkoutsViewModel.Factory

    private val viewModel by viewModelCreator<WorkoutsViewModel>{ factory.create(args.libraryId) }

    private val adapter = initAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWorkoutsBinding.bind(view)
        binding.rvWorkouts.adapter = adapter
        setupObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.listWorkoutsFlow.collect{
                adapter.listWorkouts = it
            }
        }
    }

    private fun initAdapter(): WorkoutsAdapter {
        return WorkoutsAdapter {
            val direction = WorkoutsFragmentDirections.actionWorkoutsFragmentToDetailFragment(it.id)
            findNavController().navigate(direction)
        }
    }
}