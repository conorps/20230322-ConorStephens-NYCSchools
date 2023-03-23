package com.example.nycschools.data

import androidx.lifecycle.LiveData
import com.example.nycschools.BuildConfig
import com.example.nycschools.api.SchoolNetwork
import com.example.nycschools.database.SchoolsDatabase
import com.example.nycschools.model.School
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching Schools and storing them on disk
 */
class SchoolRepository(private val database: SchoolsDatabase) {
    val schools: LiveData<List<School>> = database.schoolDao.getSchools()

    suspend fun refreshSchools() {
        withContext(Dispatchers.IO) {
            val schoolList = SchoolNetwork.schools.getSchoolList(BuildConfig.API_KEY)
            database.schoolDao.insertAll(schoolList)
        }
    }
}