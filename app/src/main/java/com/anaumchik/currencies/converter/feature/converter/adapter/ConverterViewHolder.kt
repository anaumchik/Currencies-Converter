package com.anaumchik.currencies.converter.feature.converter.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anaumchik.currencies.converter.models.Currency
import com.anaumchik.currencies.converter.utils.round
import kotlinx.android.synthetic.main.item_converter.view.countryCurrencyTv
import kotlinx.android.synthetic.main.item_converter.view.countryImg
import kotlinx.android.synthetic.main.item_converter.view.countryNameTv
import kotlinx.android.synthetic.main.item_converter.view.currencyEt

class ConverterViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private var imgCountry: ImageView = view.countryImg
    private var tvCountryName: TextView = view.countryNameTv
    private var tvCountryCurrency: TextView = view.countryCurrencyTv
    private var etCurrency: EditText = view.currencyEt
    private var isInit = false

    fun initViewHolder(currency: Currency, baseCurrency: Currency, listener: ConverterAdapterListener) {
        imgCountry.setImageDrawable(view.context.getDrawable(currency.currencyIconRes))
        tvCountryName.text = currency.countryName
        tvCountryCurrency.text = currency.countryCurrency

        if (currency.countryName == baseCurrency.countryName) {
            etCurrency.setText(baseCurrency.currencyTotal.round(ConverterAdapter.ROUND_TWO_PLACES).toString())
        } else {
            val total = baseCurrency.currencyTotal * currency.currencyRate
            etCurrency.setText(total.round(ConverterAdapter.ROUND_TWO_PLACES).toString())
        }
        isInit = true

        etCurrency.setOnFocusChangeListener { _, hasFocus ->
            if (!isBaseCurrency() && hasFocus) {
                listener.onItemMoveToTop(adapterPosition)
            }
        }

        etCurrency.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                if (isInit && isBaseCurrency()) {
                    if (s.toString().isNotEmpty()) {
                        listener.onUpdateCurrencies(s.toString().toDouble())
                    }
                    isInit = false
                }
            }
        })
    }

    private fun isBaseCurrency(): Boolean {
        return adapterPosition == ConverterAdapter.POSITION_ZERO
    }
}

interface ConverterAdapterListener {
    fun onItemMoveToTop(position: Int)
    fun onUpdateCurrencies(total: Double)
}
