package com.example.gifbrowserapp.data.utils

import android.content.SharedPreferences
import com.example.gifbrowserapp.data.errors.EmptyBodyException
import com.example.gifbrowserapp.presentation.utils.validation.Resource
import com.google.gson.JsonElement
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID


fun String?.toJsonObject(): JsonObject? = runCatching {
    Json.parseToJsonElement(this!!).jsonObject
}.getOrNull()

fun String?.getValueOf(key: String) = runCatching {
    toJsonObject()?.get(key)?.jsonPrimitive?.content
}.getOrNull()