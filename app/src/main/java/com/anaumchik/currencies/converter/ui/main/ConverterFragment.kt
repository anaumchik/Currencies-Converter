package com.anaumchik.currencies.converter.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anaumchik.weather.R

import org.koin.android.viewmodel.ext.android.viewModel

class ConverterFragment : Fragment() {

    private val viewModel: ConverterViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_converter, container, false)
    }
}