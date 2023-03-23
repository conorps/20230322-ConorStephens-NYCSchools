package com.example.nycschools.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.nycschools.model.School

@Dao
interface SchoolDao {
    @Query("select * from school")
    fun getSchools(): LiveData<List<School>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(schools: List<School>)
}

@Database(entities = [School::class], version = 1)
abstract class SchoolsDatabase : RoomDatabase() {
    abstract val schoolDao: SchoolDao
}

private lateinit var INSTANCE: SchoolsDatabase

fun getDatabase(context: Context): SchoolsDatabase {
    synchronized(SchoolsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                SchoolsDatabase::class.java, "schools"
            ).build()
        }
    }
    return INSTANCE
}