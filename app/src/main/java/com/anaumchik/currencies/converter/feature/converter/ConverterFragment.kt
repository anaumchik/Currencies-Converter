package com.anaumchik.currencies.converter.feature.converter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.anaumchik.currencies.converter.R

import org.koin.android.viewmodel.ext.android.viewModel

class ConverterFragment : Fragment() {

    private val viewModel: ConverterViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_converter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currenciesLiveData.observe(viewLifecycleOwner, Observer { currencies ->
            Log.d("ConverterFragment", "received from server: $currencies")
            // TODO show in UI
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.startUpdates()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopUpdates()
    }
}