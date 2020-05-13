package com.example.android.covid19tracker.network

import com.example.android.covid19tracker.domain.GeneralStats
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private val BASE_URL = "https://corona-virus-stats.herokuapp.com"

interface Covid19Service {
    @GET("api/v1/cases/general-stats")
    fun getGeneralStats(): Deferred<String>

    //@GET("api/v1/cases/countries-search")
    //fun getCountryStats():
}

object Network {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val covid19Service = retrofit.create(Covid19Service::class.java)
}