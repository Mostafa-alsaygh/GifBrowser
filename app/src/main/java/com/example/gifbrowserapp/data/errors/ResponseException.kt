package com.example.gifbrowserapp.data.errors

import com.example.gifbrowserapp.presentation.utils.validation.BaseException

data class ResponseException(
    override val message: String?,
    override val code: Int
) : BaseException()
