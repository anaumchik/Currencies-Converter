package com.anaumchik.currencies.converter.feature.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anaumchik.currencies.converter.feature.converter.interactor.ConverterInteractor
import com.anaumchik.currencies.converter.models.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ConverterViewModel(private val converterInteractor: ConverterInteractor) : ViewModel() {

    private val _currenciesLiveData = MutableLiveData<List<Currency>>()
    val currenciesLiveData: LiveData<List<Currency>> = _currenciesLiveData

    fun startUpdates() {
        viewModelScope.launch(Dispatchers.IO) {
//            while (true) {
            getCurrencies()
//                delay(1000)
//            }
        }
    }

    fun stopUpdates() {
        viewModelScope.cancel()
    }

    fun onUpdateCurrencies(total: Double) {
        val updatedCurrencies = _currenciesLiveData.value
            ?.toMutableList()
            ?.apply {
                val baseCurrency = this.first()

                val newCurrencyTotal = total * baseCurrency.currencyRate
                val newBaseCurrency = baseCurrency.copy(currencyTotal = newCurrencyTotal)

                this[0] = newBaseCurrency
            }

        _currenciesLiveData.postValue(updatedCurrencies)
    }

    private suspend fun getCurrencies() {
        val currencies = converterInteractor.getCurrencies()
        _currenciesLiveData.postValue(currencies)
    }
}
