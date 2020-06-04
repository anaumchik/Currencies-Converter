package com.anaumchik.currencies.converter.feature.converter.interactor

import com.anaumchik.currencies.converter.R
import com.anaumchik.currencies.converter.feature.converter.repository.ConverterRepository
import com.anaumchik.currencies.converter.models.Currency
import com.anaumchik.currencies.converter.utils.ConverterUtils

class ConverterInteractorImpl(
    private val converterRepository: ConverterRepository,
    private val converterUtils: ConverterUtils
) : ConverterInteractor {

    private val defaultCurrency = Currency(
        currencyIconRes = R.drawable.ic_eu,
        countryName = DEFAULT_COUNTRY_NAME,
        countryCurrency = DEFAULT_COUNTRY_CURRENCY,
        currencyTotal = DEFAULT_CURRENCY_TOTAL
    )

    override suspend fun getCurrencies(): List<Currency> {
        val currencies = mutableListOf<Currency>()
        currencies.add(defaultCurrency)
        converterRepository.getCurrencies().rates.forEach {
            if (converterUtils.mapExistCountries(it.key)) {
                val currency = Currency(
                    countryName = it.key,
                    countryCurrency = converterUtils.mapToCountryCurrency(it.key),
                    currencyRate = it.value,
                    currencyIconRes = converterUtils.mapToCountryIconRes(it.key)
                )
                currencies.add(currency)
            }
        }

        return currencies
    }

    companion object {
        private const val DEFAULT_COUNTRY_NAME = "EUR"
        private const val DEFAULT_COUNTRY_CURRENCY = "EUR"
        private const val DEFAULT_CURRENCY_TOTAL = 100.00
    }
}
