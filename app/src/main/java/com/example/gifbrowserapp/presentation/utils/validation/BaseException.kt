package com.example.gifbrowserapp.presentation.utils.validation

abstract class BaseException : Throwable() {
    abstract override val message: String?
    abstract val code: Int
}