package com.example.fitnesss.screens.calculator

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.fitnesss.R
import com.example.fitnesss.databinding.FragmentCalculatorBinding
import com.example.fitnesss.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CalculatorFragment : Fragment(R.layout.fragment_calculator) {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("FragmentCalculatorBinding is null")

    private val viewModel by viewModels<CalculatorViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCalculatorBinding.bind(view)
        setupView()
        setupListeners()
        setupObserver()
    }

    private fun setupView() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_gender,
            resources.getStringArray(R.array.genders)
        )
        binding.editGender.setAdapter(adapter)
        binding.editGender.setDropDownBackgroundResource(R.color.grey03)
    }

    private fun setupListeners() {
        binding.button.setOnClickListener {
            it.hideKeyboard()
            if (checkValidInput()) {
                viewModel.onButtonClick(
                    age = binding.editAge.text.toString().toInt(),
                    height = binding.editHeight.text.toString().toInt(),
                    weight = binding.editWeight.text.toString().toInt(),
                    gender = binding.editGender.text.toString()
                )
            }
        }
    }

    private fun setupObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.isLoadingFlow.collect {
                if (it) {
                    binding.progressBar.isVisible = true
                    binding.button.text = ""
                } else {
                    binding.progressBar.isVisible = false
                    binding.button.text = getString(R.string.calculate)
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.indexAndWeightFlow.collect { result ->
                if (result != null) {
                    binding.bmi.text = result.bmi.toString()
                    binding.health.text = result.health
                    binding.range.text = result.healthyBmiRange
                    binding.idealWeight.text = result.robinson.toString()
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.showContentFlow.collect {
                binding.resultGroup.isVisible = it
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.commandFlow.collectLatest { command ->
                when (command) {
                    is CalculatorViewModel.Commands.ShowBottomNetworkError -> {
                        Snackbar.make(binding.root, getString(command.message), Snackbar.LENGTH_SHORT).show()
                    }
                    is CalculatorViewModel.Commands.ShowBottomUnknown -> {
                        Snackbar.make(binding.root, getString(command.message), Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun checkValidInput(): Boolean {
        if (binding.editAge.text.isNullOrBlank()) {
            binding.age.error = getString(R.string.input_error)
            binding.age.isErrorEnabled = true
            return false
        }
        if (binding.editHeight.text.isNullOrBlank()) {
            binding.height.error = getString(R.string.input_error)
            binding.height.isErrorEnabled = true
            return false
        }
        if (binding.editWeight.text.isNullOrBlank()) {
            binding.weight.error = getString(R.string.input_error)
            binding.weight.isErrorEnabled = true
            return false
        }
        if (binding.editGender.text.isNullOrBlank()) {
            binding.gender.error = getString(R.string.input_error)
            binding.gender.isErrorEnabled = true
            return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
