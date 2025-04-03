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
    private val crimeIdLD = MutableLiveData<String>()

    var crimeLD: LiveData<Crime?> = crimeIdLD.switchMap { crimeId ->
        crimeRepository.getCrime(crimeId)
    }

    fun loadCrime(crimeId: String){
        Log.d(TAG, "loadCrime called with ID: $crimeId")
        crimeIdLD.value = crimeId
    }
}