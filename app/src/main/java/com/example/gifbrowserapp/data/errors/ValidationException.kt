package com.example.gifbrowserapp.data.errors

import com.example.gifbrowserapp.presentation.utils.validation.BaseException
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpStatus

data class ValidationException(
    override val message: String?,
    override val code: Int = HttpStatus.SC_UNPROCESSABLE_ENTITY
) : BaseException()

