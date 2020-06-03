package com.anaumchik.currencies.converter.feature.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anaumchik.currencies.converter.R
import com.anaumchik.currencies.converter.feature.converter.interactor.ConverterInteractor
import com.anaumchik.currencies.converter.models.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ConverterViewModel(private val converterInteractor: ConverterInteractor) : ViewModel() {

    private val _currenciesLiveData = MutableLiveData<List<Currency>>()
    val currenciesLiveData: LiveData<List<Currency>> = _currenciesLiveData

    fun startUpdates() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                getCurrencies()
                delay(1000)
            }
        }
    }

    fun stopUpdates() {
        viewModelScope.cancel()
    }

    private suspend fun getCurrencies() {
//        val currencies = converterInteractor.getCurrencies()
        val currencies = listOf<Currency>(Currency("EUR", "Euro", 8193.43, R.drawable.ic_eu))
        _currenciesLiveData.postValue(currencies)
    }
}
