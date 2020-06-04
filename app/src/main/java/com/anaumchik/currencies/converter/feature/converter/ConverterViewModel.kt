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

    fun onStartUpdates() {
        viewModelScope.launch(Dispatchers.IO) {
//            while (true) {
            getCurrencies()
//                delay(1000)
//            }
        }
    }

    fun onStopUpdates() {
        viewModelScope.cancel()
    }

    fun onUpdateCurrencies(baseTotal: Double) {
        val updatedCurrencies = updateTotal(_currenciesLiveData.value, baseTotal)
        _currenciesLiveData.postValue(updatedCurrencies)
    }

    fun onUpdateBaseRate(newBaseRate: Double) {
        val updatedCurrencies = updateTotalAndRate(_currenciesLiveData.value, newBaseRate)
        _currenciesLiveData.postValue(updatedCurrencies)
    }

    private suspend fun getCurrencies() {
        val currencies = converterInteractor.getCurrencies()

        val baseCurrency = currencies.first()
        val baseTotal = baseCurrency.currencyRate * baseCurrency.currencyTotal

        val updatedCurrencies = updateTotal(currencies, baseTotal)
        _currenciesLiveData.postValue(updatedCurrencies)
    }

    private fun updateTotal(currencies: List<Currency>?, baseTotal: Double): List<Currency>? = currencies
        ?.toMutableList()
        ?.mapIndexed { index, currency ->
            if (index == 0) {
                currency.copy(currencyTotal = baseTotal)
            } else {
                currency.copy(currencyTotal = baseTotal * currency.currencyRate)
            }
        }

    private fun updateTotalAndRate(currencies: List<Currency>?, newBaseRate: Double): List<Currency>? = currencies
        ?.toMutableList()
        ?.mapIndexed { index, currency ->
            if (index == 0) {
                currency.copy(currencyRate = 1.0)
            } else {
                currency.copy(currencyRate = newBaseRate / currency.currencyRate)
            }
        }
}
