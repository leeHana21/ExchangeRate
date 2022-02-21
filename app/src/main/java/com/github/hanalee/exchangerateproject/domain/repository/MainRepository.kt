package com.github.hanalee.exchangerateproject.domain.repository

import com.github.hanalee.exchangerateproject.domain.retrofit.MainApiClient


class MainRepository {
    suspend fun getExchangeRate() =
        MainApiClient.getInstance().getExchangeRate()
}