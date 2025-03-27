package com.example.criminalintent

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import java.util.UUID

private const val TAG = "CrimeDetail"

class CrimeDetailViewModel: ViewModel() {
    private val crimeRepository = CrimeRepository.get()
    private val crimeIdLD = MutableLiveData<UUID>()

    var crimeLD: LiveData<Crime?> = crimeIdLD.switchMap { crimeId ->

                val result = crimeRepository.getCrime(crimeId)
//        val crime = crimeRepository.getCrimeSync(crimeId)

        Log.d(TAG, "Crime fetched from DB: $result")
        result
    }

//    init {
//        Log.d(TAG, "CrimeDetailViewModel initialized")
//    }

    fun loadCrime(crimeId: UUID){
        Log.d(TAG, "loadCrime called with ID: $crimeId")
        crimeIdLD.value = crimeId
    }
}