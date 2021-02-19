package com.mybclym.androidacademy.petproject.dataModel.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun toListOfString(flatStringList: String): List<String> {
        return (flatStringList.split(","))
    }

    @TypeConverter
    fun fromListOfString(listOfStrings: List<String>): String {
        return listOfStrings.joinToString(",")
    }
}