package com.example.gifbrowserapp.data.entities.gifData


import com.google.gson.annotations.SerializedName

data class Analytics(
    @SerializedName("onclick")
    val onclick: Onclick?,
    @SerializedName("onload")
    val onload: Onload?,
    @SerializedName("onsent")
    val onsent: Onsent?
)