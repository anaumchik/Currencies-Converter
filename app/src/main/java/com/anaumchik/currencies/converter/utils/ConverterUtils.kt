package com.anaumchik.currencies.converter.utils

import android.content.Context
import com.anaumchik.currencies.converter.R

class ConverterUtils(private val context: Context) {

    fun mapExistCountries(county: String): Boolean = when (county) {
        EUR, USD, CAD, SEK -> true
        else -> false
    }

    fun mapToCountryCurrency(county: String): String = when (county) {
        EUR -> context.getString(R.string.currency_eur)
        USD -> context.getString(R.string.currency_usd)
        CAD -> context.getString(R.string.currency_cad)
        SEK -> context.getString(R.string.currency_sek)
        else -> ""
    }

    fun mapToCountryIconRes(county: String): Int = when (county) {
        EUR -> R.drawable.ic_eu
        USD -> R.drawable.ic_us
        CAD -> R.drawable.ic_canada
        SEK -> R.drawable.ic_sweden
        else -> 0
    }

    companion object {
        private const val EUR = "EUR"
        private const val USD = "USD"
        private const val CAD = "CAD"
        private const val SEK = "SEK"
    }
}
