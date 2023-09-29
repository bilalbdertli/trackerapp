package com.sabanciuniv.todoapp.model

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object FoodDataSerializer: Serializer<FoodData> {
    override val defaultValue: FoodData
        get() = FoodData()

    override suspend fun readFrom(input: InputStream): FoodData {
        return try{
            Json.decodeFromString(
                deserializer = FoodData.serializer(),
                string = input.readBytes().decodeToString()
            )
        }
        catch (e: SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: FoodData, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = FoodData.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }

}