package com.example.gifbrowserapp.data.entities.remote


import com.google.gson.annotations.SerializedName

data class ApiResponseRemote<T>(
    @SerializedName("data")
    val data: List<T>,
    @SerializedName("meta")
    val meta: Meta?,
)