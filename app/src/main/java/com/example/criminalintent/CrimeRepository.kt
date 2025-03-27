package com.example.criminalintent

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.identity.util.UUID
import com.example.criminalintent.database.CrimeDatabase
import kotlinx.coroutines.runBlocking


private const val DATABASE_NAME = "crime-database"
private const val TAG = "CrimeRepo"

class CrimeRepository constructor(context: Context) {

    private val database: CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME,
        )
//        .addMigrations(MIGRATION_1_2)
//        .fallbackToDestructiveMigration()
        .build()


    private val crimeDao = database.crimeDao()

    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()
    fun getCrime(id: java.util.UUID): LiveData<Crime?> = crimeDao.getCrime(id)

//    fun getCrimeSync(crimeId: java.util.UUID): LiveData<Crime?> {
//        return runBlocking {
//            crimeDao.getCrimeSync(crimeId)
//        }
//    }

    companion object{
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context){
            if(INSTANCE == null)
                INSTANCE = CrimeRepository(context)
        }

        fun get(): CrimeRepository {
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initial")
        }
    }
}