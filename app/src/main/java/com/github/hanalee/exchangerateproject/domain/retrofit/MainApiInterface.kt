package com.github.hanalee.exchangerateproject.domain.retrofit

import com.github.hanalee.exchangerateproject.BuildConfig
import com.github.hanalee.exchangerateproject.domain.retrofit.entity.response.RateResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MainApiInterface {
    @Headers("Content-Type: application/json")
    @GET("live")
    suspend fun getExchangeRate(@Query("access_key") key: String? = BuildConfig.ACCESS_KEY): RateResponse
}