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

    fun initViewHolder(currency: Currency, baseCurrency: Currency, listener: ConverterAdapterListener) {
        imgCountry.setImageDrawable(view.context.getDrawable(currency.currencyIconRes))
        tvCountryName.text = currency.countryName
        tvCountryCurrency.text = currency.countryCurrency

        if (currency.countryName == baseCurrency.countryName) {
            etCurrency.setText(baseCurrency.currencyTotal.toString())
        } else {
            val total = baseCurrency.currencyTotal * currency.currencyRate
            etCurrency.setText(total.round(ConverterAdapter.ROUND_TWO_PLACES).toString())
        }

        etCurrency.setOnFocusChangeListener { _, hasFocus ->
            if (adapterPosition != ConverterAdapter.POSITION_ZERO && hasFocus) listener.onMoveToTop(adapterPosition)
        }

        etCurrency.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun afterTextChanged(s: Editable?) =
                listener.onUpdateValues(etCurrency.text.toString().toDouble())

        })
    }
}

interface ConverterAdapterListener {
    fun onMoveToTop(position: Int)
    fun onUpdateValues(value: Double)
}
