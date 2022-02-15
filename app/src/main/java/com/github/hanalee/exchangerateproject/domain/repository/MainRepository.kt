package com.github.hanalee.exchangerateproject.domain.repository

import com.github.hanalee.exchangerateproject.domain.retrofit.MainApiClient

/**
 * main 관련 Repository
 */
class MainRepository {
    suspend fun getExchangeRate() =
        MainApiClient.getInstance().getExchangeRate()
}