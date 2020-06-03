package com.anaumchik.currencies.converter.feature.converter.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anaumchik.currencies.converter.R
import com.anaumchik.currencies.converter.models.Currency
import com.anaumchik.currencies.converter.utils.inflate

class ConverterAdapter : RecyclerView.Adapter<ConverterViewHolder>() {

    var data = listOf<Currency>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    lateinit var listener: ConverterAdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConverterViewHolder =
        ConverterViewHolder(parent.inflate(R.layout.item_converter))

    override fun onBindViewHolder(holder: ConverterViewHolder, position: Int) {
        val currency = data[position]
        holder.initViewHolder(currency, listener)
    }

    override fun getItemCount(): Int = data.size
}
