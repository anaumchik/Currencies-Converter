package com.anaumchik.currencies.converter.feature.converter.interactor

import com.anaumchik.currencies.converter.feature.converter.repository.ConverterRepository
import com.anaumchik.currencies.converter.models.Currency
import com.anaumchik.currencies.converter.utils.ConverterUtils

class ConverterInteractorImpl(
    private val converterRepository: ConverterRepository,
    private val converterUtils: ConverterUtils
) : ConverterInteractor {

    override suspend fun getCurrencies(): List<Currency> {
        val currencies = mutableListOf<Currency>()
        converterRepository.getCurrencies().rates.forEach {
            if (converterUtils.mapExistCountries(it.key)) {
                val currency = Currency(
                    countryName = it.key,
                    countryCurrency = converterUtils.mapToCountryCurrency(it.key),
                    currencyValue = it.value,
                    currencyIconRes = converterUtils.mapToCountryIconRes(it.key)
                )
                currencies.add(currency)
            }
        }

        return currencies
    }
}
