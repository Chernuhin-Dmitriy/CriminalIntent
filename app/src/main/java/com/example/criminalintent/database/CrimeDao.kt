package com.example.criminalintent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.criminalintent.Crime
import java.util.UUID

@Dao
interface CrimeDao {
    @Query ("SELECT * FROM crime")
    fun getCrimes(): LiveData<List<Crime>>

    @Query("SELECT * FROM crime where id =(:id)")
       fun getCrime(id: UUID): LiveData<Crime?>

//    @Query("INSERT INTO crime (title, date, isSolved, requiresPolice) VALUES ("Произошло утром", Date.today, false, 0)")
}
