package com.example.gifbrowserapp.presentation.features.base

import com.example.gifbrowserapp.presentation.utils.validation.ValidationResult


abstract class BaseUInputValidation {
    abstract val attributes: List<ValidationResult>

    val isAllValid: Boolean
        get() = attributes.all { it.errorState.not() }
}
