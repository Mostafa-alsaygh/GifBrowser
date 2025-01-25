package com.example.gifbrowserapp.data.local

import androidx.datastore.core.Serializer
import com.example.gifbrowserapp.data.entities.local.ResentSearch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object ResentSearchesSerializer : Serializer<List<ResentSearch>> {

    override val defaultValue: List<ResentSearch>
        get() = emptyList()

    override suspend fun readFrom(input: InputStream): List<ResentSearch> {
        return try {
            Json.decodeFromString(
                deserializer = ListSerializer(ResentSearch.serializer()),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: List<ResentSearch>, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = ListSerializer(ResentSearch.serializer()), // Serialize the list
                value = t
            ).encodeToByteArray()
        )
    }
}
