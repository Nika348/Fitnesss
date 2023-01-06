package com.example.fitnesss.screens.calculator

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.fitnesss.R
import com.example.fitnesss.databinding.FragmentCalculatorBinding
import com.example.fitnesss.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
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
            viewModel.onButtonClick(
                age = binding.editAge.text.toString().toInt(),
                height = binding.editHeight.text.toString().toInt(),
                weight = binding.editWeight.text.toString().toInt(),
                gender = binding.editGender.text.toString()
            )
        }

        binding.editAge.setOnFocusChangeListener { _, hasFocus ->
            viewModel.onEditAgeFocusListener(hasFocus, binding.editAge.text.toString())
        }
        binding.editWeight.setOnFocusChangeListener { _, hasFocus ->
            viewModel.onEditWeightFocusListener(hasFocus, binding.editWeight.text.toString())
        }
        binding.editHeight.setOnFocusChangeListener { _, hasFocus ->
            viewModel.onEditHeightFocusListener(hasFocus, binding.editHeight.text.toString())
        }
        binding.editGender.setOnFocusChangeListener { _, hasFocus ->
            viewModel.onEditGenderFocusListener(hasFocus, binding.editGender.text.toString())
        }
        binding.editAge.doAfterTextChanged {
            checkValidInput()
        }
        binding.editWeight.doAfterTextChanged {
            checkValidInput()
        }
        binding.editHeight.doAfterTextChanged {
            checkValidInput()
        }
        binding.editGender.setOnItemClickListener { _, _, _, _ ->
            checkValidInput()
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
                        Snackbar.make(
                            binding.root,
                            getString(command.message),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    is CalculatorViewModel.Commands.ShowBottomUnknown -> {
                        Snackbar.make(
                            binding.root,
                            getString(command.message),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.editAgeStateFlow.collect { state ->
                with(binding) {
                    when (state) {
                        is CalculatorViewModel.EditAgeState.DiapasonError -> {
                            age.error = getString(state.message)
                            age.isErrorEnabled = true
                        }
                        is CalculatorViewModel.EditAgeState.EmptyFieldError -> {
                            age.error = getString(state.message)
                            age.isErrorEnabled = true
                        }
                        CalculatorViewModel.EditAgeState.ValidState -> {
                            age.error = null
                            age.isErrorEnabled = false
                        }
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.editHeightStateFlow.collect { state ->
                with(binding) {
                    when (state) {
                        is CalculatorViewModel.EditHeightState.DiapasonError -> {
                            height.error = getString(state.message)
                            height.isErrorEnabled = true
                        }
                        is CalculatorViewModel.EditHeightState.EmptyFieldError -> {
                            height.error = getString(state.message)
                            height.isErrorEnabled = true
                        }
                        CalculatorViewModel.EditHeightState.ValidState -> {
                            height.error = null
                            height.isErrorEnabled = false
                        }
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.editWeightStateFlow.collect { state ->
                with(binding) {
                    when (state) {
                        is CalculatorViewModel.EditWeightState.DiapasonError -> {
                            weight.error = getString(state.message)
                            weight.isErrorEnabled = true
                        }
                        is CalculatorViewModel.EditWeightState.EmptyFieldError -> {
                            weight.error = getString(state.message)
                            weight.isErrorEnabled = true
                        }
                        CalculatorViewModel.EditWeightState.ValidState -> {
                            weight.error = null
                            weight.isErrorEnabled = false
                        }
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.editGenderStateFlow.collect { state ->
                with(binding) {
                    when (state) {
                        is CalculatorViewModel.EditGenderState.EmptyFieldError -> {
                            gender.error = getString(state.message)
                            gender.isErrorEnabled = true
                        }
                        CalculatorViewModel.EditGenderState.ValidState -> {
                            gender.error = null
                            gender.isErrorEnabled = false
                        }
                    }
                }
            }
        }
    }

    private fun checkValidInput() {
        with(binding) {
            button.isEnabled =
                (!editAge.text.isNullOrBlank() && editAge.text.toString().toInt() in 0..80)
                        && (!editHeight.text.isNullOrBlank() && editHeight.text.toString()
                    .toInt() in 130..230)
                        && (!editWeight.text.isNullOrBlank() && editWeight.text.toString()
                    .toInt() in 40..160)
                        && editGender.text.isNotBlank()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
