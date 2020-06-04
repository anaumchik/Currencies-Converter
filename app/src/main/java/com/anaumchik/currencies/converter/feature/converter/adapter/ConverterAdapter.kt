package com.anaumchik.currencies.converter.feature.converter.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anaumchik.currencies.converter.R
import com.anaumchik.currencies.converter.models.Currency
import com.anaumchik.currencies.converter.utils.inflate
import java.util.Collections

class ConverterAdapter : RecyclerView.Adapter<ConverterViewHolder>() {

    private var data = listOf<Currency>()

    lateinit var listener: ConverterAdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConverterViewHolder =
        ConverterViewHolder(parent.inflate(R.layout.item_converter))

    override fun onBindViewHolder(holder: ConverterViewHolder, position: Int) {
        val currency = data[position]
        val baseCurrency = data[BASE_CURRENCY_POSITION]
        holder.initViewHolder(currency, baseCurrency, listener)
    }

    override fun getItemCount(): Int = data.size

    fun onUpdateData(newData: List<Currency>) {
        data = newData
        notifyDataSetChanged()
    }

    fun onItemMoveToTop(fromPosition: Int) {
        Collections.swap(data, fromPosition, BASE_CURRENCY_POSITION)
        notifyItemMoved(fromPosition, BASE_CURRENCY_POSITION)
        listener.onUpdateBaseRate(
            data[BASE_CURRENCY_POSITION].currencyRate,
            data[BASE_CURRENCY_POSITION].currencyTotal
        )
    }

    companion object {
        const val BASE_CURRENCY_POSITION = 0
    }
}
