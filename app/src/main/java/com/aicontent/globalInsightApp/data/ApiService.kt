package com.aicontent.globalInsightApp.data

import android.graphics.Region
import com.aicontent.globalInsightApp.entity.modelAll.entity
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("all")
    suspend fun getAllCountry(): entity // entity : ArrayList

    @GET("name/{name}")
    suspend fun getCountryByName(@Path("name") name: String): entity

    @GET("lang/{language}")
    suspend fun getCountryByLanguage(@Path("language") lang: String): entity


    @GET("{service}?fields={field}")
    suspend fun getCountryByRegion(@Path("field") field: String): List<String>
}