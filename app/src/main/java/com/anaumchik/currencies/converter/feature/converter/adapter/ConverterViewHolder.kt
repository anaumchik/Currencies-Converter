package com.anaumchik.currencies.converter.feature.converter.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anaumchik.currencies.converter.models.Currency
import com.anaumchik.currencies.converter.utils.ROUND_TWO_PLACES
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

    fun initViewHolder(currency: Currency, baseCurrency: Currency, listener: ConverterAdapterListener) {
        this.currency = currency
        this.listener = listener
        this.baseCurrency = baseCurrency

        initBlock()
        setCurrencyHintIfTotalZero()
        setListenerToSwapItemToTop()
        setListenerToUpdateCurrencies()
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

        isInit = true
    }

    private fun setCurrencyHintIfTotalZero() {
        if (currency.currencyTotal == 0.0) {
            etCurrency.setText("")
        } else {
            isZero = false
        }
    }

    private fun setListenerToSwapItemToTop() {
        etCurrency.setOnFocusChangeListener { _, hasFocus ->
            if (!isBaseCurrency() && hasFocus) {
                listener.onItemMoveToTop(adapterPosition)
            }
        }
    }

    private fun setListenerToUpdateCurrencies() {
        etCurrency.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                if (isInit && isBaseCurrency()) {
                    if (s.toString().isNotEmpty()) {
                        val baseTotal = s.toString().toDouble() * currency.currencyRate
                        updateCurrencies(baseTotal)
                    } else {
                        if (!isZero) {
                            updateCurrencies(0.0)
                            isZero = !isZero
                        }
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
}
