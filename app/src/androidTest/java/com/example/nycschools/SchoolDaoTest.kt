package com.example.nycschools

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nycschools.database.SchoolDao
import com.example.nycschools.database.SchoolsDatabase
import com.example.nycschools.model.School
import com.example.nycschools.utilities.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class SchoolDaoTest {
    private lateinit var database: SchoolsDatabase
    private lateinit var schoolDao: SchoolDao
    private val schoolA = School("XXXXXX", "SCHOOL A", "K")
    private val schoolB = School("YYYYYY", "SCHOOL B", "Q")
    private val schoolC = School("ZZZZZZ", "SCHOOL C", "X")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Before fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, SchoolsDatabase::class.java).build()
        schoolDao = database.schoolDao

        schoolDao.insertAll(listOf(schoolA, schoolB, schoolC))
    }

    @After fun closeDb() {
        database.close()
    }

    @Test fun testGetSchools() = runBlocking {
        val schoolList = schoolDao.getSchools()
        MatcherAssert.assertThat(getValue(schoolList).size, equalTo(3))

        MatcherAssert.assertThat(getValue(schoolList)[0], Matchers.equalTo(schoolA))
        MatcherAssert.assertThat(getValue(schoolList)[1], Matchers.equalTo(schoolB))
        MatcherAssert.assertThat(getValue(schoolList)[2], Matchers.equalTo(schoolC))
    }
}