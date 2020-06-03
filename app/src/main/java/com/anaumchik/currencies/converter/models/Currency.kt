package com.anaumchik.currencies.converter.models

import androidx.annotation.DrawableRes

data class Currency(
    @DrawableRes val currencyIconRes: Int,
    val countryName: String,
    val countryCurrency: String,
    val currencyRate: Double = 1.0,
    val currencyTotal: Double = 0.0
)
