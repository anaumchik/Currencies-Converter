package com.anaumchik.currencies.converter.feature.converter.adapter

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anaumchik.currencies.converter.models.Currency
import kotlinx.android.synthetic.main.item_converter.view.countryCurrencyTv
import kotlinx.android.synthetic.main.item_converter.view.countryImg
import kotlinx.android.synthetic.main.item_converter.view.countryNameTv
import kotlinx.android.synthetic.main.item_converter.view.currencyEt

class ConverterViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var listener: ConverterAdapterListener
    private lateinit var currency: Currency
    private var imgCountry: ImageView = view.countryImg
    private var tvCountryName: TextView = view.countryNameTv
    private var tvCountryCurrency: TextView = view.countryCurrencyTv
    private var etCurrency: EditText = view.currencyEt

    fun initViewHolder(currency: Currency, listener: ConverterAdapterListener) {
        this.currency = currency
        this.listener = listener
        imgCountry.setImageDrawable(view.context.getDrawable(currency.currencyIconRes))
        tvCountryName.text = currency.countryName
        tvCountryCurrency.text = currency.countryCurrency
        etCurrency.setText(currency.currencyValue.toString())
        // TODO et
    }
}

interface ConverterAdapterListener {
    fun onUpdate(update: Unit)
}
