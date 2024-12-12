package com.example.criminalintent.database

import androidx.room.TypeConverter
import com.android.identity.util.UUID
import java.util.Date

class CrimeTypeConverters {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(miliSinceEpoche: Long?): Date? {
        return miliSinceEpoche?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return uuid?.let {UUID.fromString(uuid)} // Выполняем, если не равен null
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }
}