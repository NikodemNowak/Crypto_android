package com.nikodem.crypto.ui.detail

import com.nikodem.crypto.services.Coin
import com.nikodem.crypto.utils.BaseViewModel
import com.nikodem.crypto.utils.ViewState

class CryptoDetailFragmentViewModel : BaseViewModel<CryptoDetailFragmentViewState>(
    initialState = CryptoDetailFragmentViewState()
) {

    fun setCoin(coin: Coin) {
        updateViewState { it.copy(coin = coin, isLoading = true) }
    }
}

data class CryptoDetailFragmentViewState(
    val coin: Coin = Coin(),
    override val isLoading: Boolean = false
) : ViewState