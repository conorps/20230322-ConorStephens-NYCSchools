package com.example.nycschools.api

import com.example.nycschools.model.School
import com.example.nycschools.model.TestScores
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface SchoolService {

    @GET("resource/s3k6-pzi2.json")
    suspend fun getSchoolList(@Header("x-api-key") apikey: String): List<School>

    //TODO: Extract string resource
    @GET("resource/f9bf-2cp4.json")
    suspend fun getTestScores(@Query("dbn") dbn: String, @Header("x-api-key") apikey: String): List<TestScores>
}

object SchoolNetwork {
    val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    val client = OkHttpClient.Builder().addInterceptor(logger).build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://data.cityofnewyork.us/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val schools = retrofit.create(SchoolService::class.java)
}