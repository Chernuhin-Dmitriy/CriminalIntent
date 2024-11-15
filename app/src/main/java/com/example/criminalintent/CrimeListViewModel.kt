package com.example.criminalintent

import androidx.lifecycle.ViewModel

class CrimeListViewModel : ViewModel() {

    var crimes = mutableListOf<Crime>()

    init {
        for(i in 1 until 100){
            val crime = Crime()
            crime.title =  "The crime #$i"
            crime.isSolved = i % 2 == 0
            crimes += crime
            crime.requiresPolice =
                if((i % 8) == 0)
                    1
                else
                    0
        }
    }
}