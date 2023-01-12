package com.loki.booko.domain

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.loki.booko.data.remote.response.Author
import com.loki.booko.data.remote.response.Formats
import com.loki.booko.data.remote.response.Translator
import java.lang.reflect.Type


class BookTypeConverter {

    val gson: Gson = Gson()

    @TypeConverter
    fun authorListToString(author: List<Author>): String {
        return gson.toJson(author)
    }

    @TypeConverter
    fun stringToAuthorList(author: String): List<Author> {

        val listType = object : TypeToken<List<Author?>?>() {}.type

        return gson.fromJson(author, listType)
    }

    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun stringToObject(value: String): Formats {
        return Gson().fromJson(value, Formats::class.java)
    }

    @TypeConverter
    fun objectToString(obj: Formats): String {
        return Gson().toJson(obj)
    }

    @TypeConverter
    fun translatorListToString(translator: List<Translator>): String {
        return gson.toJson(translator)
    }

    @TypeConverter
    fun translatorStringToList(translator: String): List<Translator> {

        val listType: Type = object : TypeToken<List<Translator?>?>() {}.type

        return gson.fromJson(translator, listType)
    }
}