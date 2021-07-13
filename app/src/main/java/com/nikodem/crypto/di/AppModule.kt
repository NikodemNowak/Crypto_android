package com.nikodem.crypto.di

import com.nikodem.crypto.repositories.CatApiRepository
import com.nikodem.crypto.repositories.CatRepository
import com.nikodem.crypto.repositories.CryptoApiRepository
import com.nikodem.crypto.repositories.CryptoRepository
import com.nikodem.crypto.ui.main.CryptoFragmentViewModel
import com.nikodem.crypto.utils.ContentProvider
import com.nikodem.crypto.utils.ContentProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single<ContentProvider> {
        ContentProviderImpl(
            context = androidContext()
        )
    }

    single<CryptoRepository> {
        CryptoApiRepository(
            cryptoApiService = get()
        )
    }

    single<CatRepository> {
        CatApiRepository(
            catApiService = get()
        )
    }

    single {
        CryptoFragmentViewModel(
            cryptoRepository = get()
        )
    }
}