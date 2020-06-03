package com.anaumchik.currencies.converter.feature.converter

import com.anaumchik.currencies.converter.network.models.CurrenciesResponse

interface ConverterRepository {

    suspend fun getCurrencies(): CurrenciesResponse
}