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


fun SharedPreferences.putString(key: String, value: String) = this.edit()
    .putString(key, value)
    .apply()


fun String?.toJsonObject(): JsonObject? = runCatching {
    Json.parseToJsonElement(this!!).jsonObject
}.getOrNull()

fun String?.getValueOf(key: String) = runCatching {
    toJsonObject()?.get(key)?.jsonPrimitive?.content
}.getOrNull()

fun <T> Resource<T>?.getOrThrowEmpty() = this?.toData ?: throw EmptyBodyException()


fun String.titlecase() = lowercase().replaceFirstChar(Char::uppercase)


fun JsonElement?.toStringList(separator: String = ";"): List<String> = this
    ?.asString
    ?.split(separator)
    ?.filter(String::isNotBlank)
    ?: emptyList()


fun String.Companion.generateRandomId(): String =
    UUID.randomUUID().toString().split('-').last()

fun <T> List<T>.serializedWithSeparator(transform: (T) -> String): String =
    joinToString(";", transform = transform)

fun String.toStringUrl(path: String = "") = StringBuilder(this).apply {
    if (!endsWith('/')) append('/')

    if (path.isNotEmpty()) {
        append(path.trimStart('/'))

        if (!endsWith('/')) append('/')
    }
}.toString()

fun LocalDate.toFormattedString(format: String = "dd/MM/yyyy"): String {
    return format(DateTimeFormatter.ofPattern(format))
}

fun String.toLocalDate(format: String = "dd/MM/yyyy"): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ofPattern(format))
}


