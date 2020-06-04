package com.anaumchik.currencies.converter.feature.converter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anaumchik.currencies.converter.R
import com.anaumchik.currencies.converter.feature.converter.adapter.ConverterAdapter
import com.anaumchik.currencies.converter.feature.converter.adapter.ConverterAdapterListener
import com.anaumchik.currencies.converter.utils.toolbarTitle
import kotlinx.android.synthetic.main.fragment_converter.converterRv
import org.koin.android.viewmodel.ext.android.viewModel

class ConverterFragment : Fragment() {

    private val viewModel: ConverterViewModel by viewModel()
    private val converterAdapter by lazy { ConverterAdapter() }

    private val converterAdapterListener = object : ConverterAdapterListener {
        override fun onItemMoveToTop(position: Int) {
            converterAdapter.onItemMoveToTop(position)
        }

        override fun onUpdateCurrencies(total: Double) {
            viewModel.onUpdateCurrencies(total)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_converter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarTitle(R.string.converter_toolbar_title)
        initAdapter()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.startUpdates()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopUpdates()
    }

    private fun observeViewModel() {
        viewModel.currenciesLiveData.observe(viewLifecycleOwner, Observer { currencies ->
            Log.d("ConverterFragment", "received from server: $currencies")
            converterAdapter.onUpdateData(currencies)
        })
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        converterRv.adapter = converterAdapter
        converterRv.layoutManager = layoutManager
        converterRv.setHasFixedSize(true)
        converterAdapter.listener = converterAdapterListener
    }
}