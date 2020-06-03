package com.anaumchik.currencies.converter

import android.app.Application
import com.anaumchik.currencies.converter.feature.converter.ConverterRepository
import com.anaumchik.currencies.converter.feature.converter.ConverterRepositoryImpl
import com.anaumchik.currencies.converter.feature.converter.ConverterViewModel
import com.anaumchik.currencies.converter.network.NetworkClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    private val modules = module {
        single<ConverterRepository> { ConverterRepositoryImpl(NetworkClient.converterApi) }
        viewModel { ConverterViewModel(repository = get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(modules))
        }
    }
}