package com.example.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.criminalintent.Crime


@Database(entities = [Crime::class], version = 1)
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDatabase : RoomDatabase() {

    abstract fun crimeDao(): CrimeDao
}

//val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        // Добавляем столбец "requiresPolice"
//        database.execSQL("ALTER TABLE Crime ADD COLUMN requiresPolice INTEGER NOT NULL DEFAULT 0")
//
//        // Обновляем столбец "test", чтобы он был NOT NULL с правильным значением по умолчанию
//        database.execSQL("ALTER TABLE Crime ADD COLUMN test INTEGER NOT NULL DEFAULT 0")
//    }
//}