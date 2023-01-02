package com.example.fitnesss.screens.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fitnesss.R
import com.example.fitnesss.databinding.FragmentFavoriteBinding
import com.example.fitnesss.screens.workouts.WorkoutsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("FragmentWorkoutsBinding is null")

    private val adapter = FavoriteAdapter(
        onItemClick = {
            val direction = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(it.id)
            findNavController().navigate(direction)
        }
    )

    private val viewModel by viewModels<FavoriteFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoriteBinding.bind(view)
        binding.rvFavorite.adapter = adapter
        setupObservers()
    }

    private fun setupObservers(){
        lifecycleScope.launchWhenCreated {
            viewModel.listFavoriteFlow.collect{
                adapter.listFavorite = it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}