package com.example.nycschools.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class School(
    @PrimaryKey var dbn: String, var school_name: String, var boro: String
) {
    fun getFullBoro(): String {
        return when (boro) {
            "K" -> "Brooklyn"
            "M" -> "Manhattan"
            "Q" -> "Queens"
            "R" -> "Staten Island"
            "X" -> "Bronx"
            else -> "--"
        }
    }
}

