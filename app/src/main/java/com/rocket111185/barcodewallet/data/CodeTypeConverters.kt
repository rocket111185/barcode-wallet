package com.rocket111185.barcodewallet.data

import androidx.room.TypeConverter

class CodeTypeConverters {
    @TypeConverter
    fun toCodeFormat(value: String): CodeFormat = CodeFormat.fromStorageValue(value)

    @TypeConverter
    fun fromCodeFormat(format: CodeFormat): String = format.storageValue

    @TypeConverter
    fun toCodeSource(value: String): CodeSource = CodeSource.fromStorageValue(value)

    @TypeConverter
    fun fromCodeSource(source: CodeSource): String = source.storageValue
}
