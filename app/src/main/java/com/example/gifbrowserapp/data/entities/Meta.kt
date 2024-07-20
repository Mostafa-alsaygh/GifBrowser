package com.example.gifbrowserapp.data.entities


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("status")
    val status: Int?
)