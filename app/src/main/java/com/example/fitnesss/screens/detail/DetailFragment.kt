package com.example.fitnesss.screens.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.fitnesss.R
import com.example.fitnesss.databinding.FragmentDetailBinding
import com.example.fitnesss.models.detail.Detail
import com.example.fitnesss.utils.viewModelCreator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("FragmentLibraryBinding is null")

    private val args by navArgs<DetailFragmentArgs>()

    @Inject
    lateinit var factory: DetailViewModel.Factory

    private val viewModel by viewModelCreator<DetailViewModel> { factory.create(args.workoutsId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.detailFlow.collect { detail ->
                detail?.let { populate(it) }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.isFavoriteFlow.collect { isFavorite ->
                binding.favorite.setImageResource(
                    if (isFavorite) R.drawable.ic_favorite
                    else R.drawable.ic_no_favorite
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun populate(detail: Detail) {
        binding.repeatExercise.text = detail.repeatExercise
        binding.timeRelaxation.text = detail.timeRelaxation
        binding.description.text = detail.descriptionExerciseDetail
        binding.exercise.text = detail.nameExerciseDetail

        Glide.with(binding.root.context)
            .load(detail.imageExerciseDetail)
            .centerCrop()
            .error(R.drawable.good_shape)
            .placeholder(R.drawable.placeholder)
            .into(binding.imageExerciseDetail)
    }

    private fun setupListeners() {
        binding.favorite.setOnClickListener {
            viewModel.updateFavoriteStatus()
        }
    }
}