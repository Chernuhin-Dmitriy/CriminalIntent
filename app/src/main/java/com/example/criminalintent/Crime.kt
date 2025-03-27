package com.example.criminalintent

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

//@Entity
//data class Crime(@PrimaryKey var id: UUID = UUID.randomUUID(),
//                 var title: String = "",
//                 var date: Date = Date(),
//                 var isSolved: Boolean = false,
//                 var requiresPolice: Int = 0,
//                 var test: Int = 0)


@Entity
data class Crime(@PrimaryKey var id: String = (UUID.randomUUID()).toString(),
                 var title: String = "",
                 var date: Int = 1,
                 var isSolved: Int = 1)