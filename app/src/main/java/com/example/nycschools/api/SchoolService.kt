package com.example.nycschools.api

import com.example.nycschools.model.School
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface SchoolService {

    @Headers("x-api-key:ZvP6RXnXF5Yt8Ra4L4DtlTDyK")
    @GET("resource/s3k6-pzi2.json")
    suspend fun getSchoolList(): List<School>

    @Headers("x-api-key:ZvP6RXnXF5Yt8Ra4L4DtlTDyK")
    @GET("resource/f9bf-2cp4.json")
    suspend fun getTestScores()

    companion object {
        fun create(): SchoolService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://data.cityofnewyork.us/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(SchoolService::class.java)
        }
    }
}