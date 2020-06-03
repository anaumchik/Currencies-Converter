package com.anaumchik.currencies.converter.network

import com.anaumchik.currencies.converter.network.NetworkClient.BASE_EUR
import com.anaumchik.currencies.converter.network.models.CurrenciesResponse
import retrofit2.http.GET

interface ConverterApi {

    @GET("api/android/latest?base=$BASE_EUR")
    suspend fun getCurrencies(): CurrenciesResponse
}