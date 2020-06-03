package com.anaumchik.currencies.converter.feature.converter

import com.anaumchik.currencies.converter.network.ConverterApi
import com.anaumchik.currencies.converter.network.models.CurrenciesResponse

class ConverterRepositoryImpl(private val api: ConverterApi) : ConverterRepository {

    override suspend fun getCurrencies(): CurrenciesResponse = api.getCurrencies()
}