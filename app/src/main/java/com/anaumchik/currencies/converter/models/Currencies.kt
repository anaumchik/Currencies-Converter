package com.anaumchik.currencies.converter.models

import androidx.annotation.DrawableRes

data class Currency(
    val countryName: String,
    val countryCurrency: String,
    val currencyValue: Double,
    @DrawableRes val currencyIconRes: Int
)
