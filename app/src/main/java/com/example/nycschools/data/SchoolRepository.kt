package com.example.nycschools.data

import com.example.nycschools.api.SchoolService
import com.example.nycschools.model.School

class SchoolRepository(private val service: SchoolService) {
    suspend fun loadSchools(): List<School> {
        return service.getSchoolList()
    }
}