package com.anaumchik.currencies.converter.feature.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anaumchik.currencies.converter.network.models.CurrenciesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ConverterViewModel(private val repository: ConverterRepository) : ViewModel() {

    private val _currenciesLiveData = MutableLiveData<CurrenciesResponse>()
    val currenciesLiveData: LiveData<CurrenciesResponse> = _currenciesLiveData

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
        val currencies = repository.getCurrencies()
        _currenciesLiveData.postValue(currencies)
    }
}
