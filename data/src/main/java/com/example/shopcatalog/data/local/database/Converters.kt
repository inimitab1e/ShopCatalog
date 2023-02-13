package com.example.shopcatalog.data.local.database

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {

    @TypeConverter
    fun fromStringToUri(value: String?): Uri? {
        return if (value == null) null else Uri.parse(value)
    }

    @TypeConverter
    fun uriToString(uri: Uri?): String? {
        return uri?.toString()
    }

    @JvmStatic
    @TypeConverter
    fun fromString(value: String): Map<Int, Int> {
        val mapType = object : TypeToken<Map<Int, Int>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    @JvmStatic
    fun fromStringMap(map: Map<Int, Int>): String {
        val gson = Gson()
        return gson.toJson(map)
    }
}