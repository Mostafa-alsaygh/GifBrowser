package com.example.gifbrowserapp.data.entities.local

import com.example.gifbrowserapp.presentation.utils.extensions.emptyString
import kotlinx.serialization.Serializable

@Serializable
data class ResentSearch(
    val searchQuery: String = emptyString(),
    val time: Long = System.currentTimeMillis(),
)