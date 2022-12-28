package com.example.fitnesss.screens.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitnesss.R
import com.example.fitnesss.databinding.FragmentDetailBinding
import com.example.fitnesss.databinding.FragmentFavoriteBinding
import com.example.fitnesss.databinding.FragmentLibraryBinding
import com.example.fitnesss.databinding.FragmentWorkoutsBinding
import com.example.fitnesss.models.detail.Detail
import com.example.fitnesss.models.library.LibraryItem
import com.example.fitnesss.models.workouts.Workouts
import com.example.fitnesss.screens.favorite.FavoriteFragmentViewModel
import com.example.fitnesss.screens.workouts.WorkoutsFragmentArgs
import com.example.fitnesss.screens.workouts.WorkoutsViewModel
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

    private val viewModel by viewModelCreator<DetailViewModel>{ factory.create(args.workoutsId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.data.collect { detail ->
                    detail?.let{ populate(it) }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun populate(detail: Detail) {
        binding.timeExercise.text = detail.time_exercise
        binding.timeRelaxation.text = detail.time_relaxation
        binding.description.text = detail.description_exercise_detail
        binding.exercise.text = detail.text_exercise_detail

        Glide.with(binding.root.context)
            .load(detail.image_exercise_detail)
            .centerCrop()
            .error(R.drawable.good_shape)
            .into(binding.imageExerciseDetail)

    }
}