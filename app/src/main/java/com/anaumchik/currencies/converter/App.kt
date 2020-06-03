package com.anaumchik.currencies.converter

import android.app.Application
import com.anaumchik.currencies.converter.feature.converter.ConverterViewModel
import com.anaumchik.currencies.converter.feature.converter.interactor.ConverterInteractor
import com.anaumchik.currencies.converter.feature.converter.interactor.ConverterInteractorImpl
import com.anaumchik.currencies.converter.feature.converter.repository.ConverterRepository
import com.anaumchik.currencies.converter.feature.converter.repository.ConverterRepositoryImpl
import com.anaumchik.currencies.converter.network.NetworkClient
import com.anaumchik.currencies.converter.utils.ConverterUtils
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    private val modules = module {
        single { ConverterUtils(context = applicationContext) }
        single<ConverterInteractor> { ConverterInteractorImpl(converterRepository = get(), converterUtils = get()) }
        single<ConverterRepository> { ConverterRepositoryImpl(converterApi = NetworkClient.converterApi) }
        viewModel { ConverterViewModel(converterInteractor = get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(modules))
        }
    }
}
