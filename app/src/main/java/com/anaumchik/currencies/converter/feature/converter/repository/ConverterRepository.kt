package com.anaumchik.currencies.converter.feature.converter.repository

import com.anaumchik.currencies.converter.network.models.CurrenciesResponse

interface ConverterRepository {

    suspend fun getCurrencies(): CurrenciesResponse
}
