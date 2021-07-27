package com.nikodem.crypto.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.nikodem.crypto.repositories.CryptoApiRepository
import com.nikodem.crypto.repositories.CryptoRepository
import com.nikodem.crypto.repositories.SharedPrefUserRepository
import com.nikodem.crypto.repositories.UserRepository
import com.nikodem.crypto.ui.detail.CryptoDetailFragmentViewModel
import com.nikodem.crypto.ui.main.CryptoFragmentViewModel
import com.nikodem.crypto.ui.settings.SettingsFragmentViewModel
import com.nikodem.crypto.ui.welcome.WelcomeFragmentViewModel
import com.nikodem.crypto.utils.ContentProvider
import com.nikodem.crypto.utils.ContentProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

private const val PREFERENCES_FILE_KEY = "com.nikodem.crypto.shared_preferences"

val appModule = module {

//    single {
//        androidApplication().getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
//    }

    single {
        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(
            PREFERENCES_FILE_KEY,
            masterKey,
            androidContext(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    } bind SharedPreferences::class

    single<UserRepository> {
        SharedPrefUserRepository(
            sharedPreferences = get()
        )
    }

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

    viewModel {
        CryptoFragmentViewModel(
            cryptoRepository = get()
        )
    }

    viewModel {
        CryptoDetailFragmentViewModel()
    }

    viewModel {
        SettingsFragmentViewModel(userRepository = get())
    }

    viewModel {
        WelcomeFragmentViewModel(userRepository = get())
    }
}