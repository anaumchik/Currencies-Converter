package com.anaumchik.currencies.converter.feature.converter.repository

import com.anaumchik.currencies.converter.network.ConverterApi
import com.anaumchik.currencies.converter.network.models.CurrenciesResponse

class ConverterRepositoryImpl(private val converterApi: ConverterApi) :
    ConverterRepository {

    override suspend fun getCurrencies(): CurrenciesResponse = converterApi.getCurrencies()
}