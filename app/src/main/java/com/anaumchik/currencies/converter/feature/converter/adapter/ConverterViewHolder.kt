package com.anaumchik.currencies.converter.feature.converter.adapter

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anaumchik.currencies.converter.models.Currency
import com.anaumchik.currencies.converter.utils.ROUND_TWO_PLACES
import com.anaumchik.currencies.converter.utils.SimpleTextWatcher
import com.anaumchik.currencies.converter.utils.round
import kotlinx.android.synthetic.main.item_converter.view.countryCurrencyTv
import kotlinx.android.synthetic.main.item_converter.view.countryImg
import kotlinx.android.synthetic.main.item_converter.view.countryNameTv
import kotlinx.android.synthetic.main.item_converter.view.currencyEt

class ConverterViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var currency: Currency
    private lateinit var baseCurrency: Currency
    private lateinit var listener: ConverterAdapterListener

    private var imgCountry: ImageView = view.countryImg
    private var tvCountryName: TextView = view.countryNameTv
    private var tvCountryCurrency: TextView = view.countryCurrencyTv
    private var etCurrency: EditText = view.currencyEt
    private var isInit = false
    private var isZero = false
    private var isSwap = false

    fun initViewHolder(currency: Currency, baseCurrency: Currency, listener: ConverterAdapterListener) {
        this.currency = currency
        this.listener = listener

        if (this::baseCurrency.isInitialized) {
            if (this.baseCurrency != baseCurrency) isSwap = true
        }

        this.baseCurrency = baseCurrency

        initBlock()
        setCurrencyHintIfTotalZero()
        initListenerToSwapItemToTop()
        initListenerToUpdateCurrencies()

        isSwap = false
    }

    private fun initBlock() {
        imgCountry.setImageDrawable(view.context.getDrawable(currency.currencyIconRes))
        tvCountryName.text = currency.countryName
        tvCountryCurrency.text = currency.countryCurrency

        if (isBaseCurrency()) {
            etCurrency.setText(baseCurrency.currencyTotal.toInt().toString())
        } else {
            etCurrency.setText(currency.currencyTotal.round(ROUND_TWO_PLACES).toString())
        }

        etCurrency.setSelection(etCurrency.text.length)

        isInit = true
    }

    private fun setCurrencyHintIfTotalZero() {
        if (currency.currencyTotal == 0.0) {
            etCurrency.setText("")
        } else {
            isZero = false
        }
    }

    private fun initListenerToSwapItemToTop() {
        etCurrency.setOnFocusChangeListener { _, hasFocus ->
            if (!isBaseCurrency() && hasFocus) {
                listener.onItemMoveToTop(adapterPosition)
            }
        }
    }

    private fun initListenerToUpdateCurrencies() {
        etCurrency.addTextChangedListener(SimpleTextWatcher {
            if (!isSwap && isInit && isBaseCurrency()) {
                if (it.toString().isNotEmpty()) {
                    val baseTotal = it.toString().toDouble() * currency.currencyRate
                    updateCurrencies(baseTotal)
                } else {
                    if (!isZero) {
                        updateCurrencies(0.0)
                        isZero = !isZero
                    }
                }
            }
        })
    }

    private fun updateCurrencies(baseTotal: Double) {
        listener.onUpdateCurrencies(baseTotal)
        isInit = false
    }

    private fun isBaseCurrency(): Boolean {
        return adapterPosition == ConverterAdapter.BASE_CURRENCY_POSITION
    }
}

interface ConverterAdapterListener {
    fun onItemMoveToTop(position: Int)
    fun onUpdateCurrencies(baseTotal: Double)
    fun onUpdateBaseRate(newBaseRate: Double, newBaseTotal: Double)
}
