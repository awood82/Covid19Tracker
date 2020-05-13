package com.example.android.covid19tracker.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val BASE_URL = "https://corona-virus-stats.herokuapp.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface Covid19Service {
    @GET("api/v1/cases/general-stats")
    fun getGeneralStats(): Deferred<NetworkGeneralContainer>

    //@GET("api/v1/cases/countries-search")
    //fun getCountryStats():
}

object CovidApi {
    val service : Covid19Service by lazy {
        retrofit.create(Covid19Service::class.java)
    }
}