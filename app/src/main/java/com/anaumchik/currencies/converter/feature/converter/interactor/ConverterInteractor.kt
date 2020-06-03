package com.anaumchik.currencies.converter.feature.converter.interactor

import com.anaumchik.currencies.converter.models.Currency

interface ConverterInteractor {

    suspend fun getCurrencies(): List<Currency>
}