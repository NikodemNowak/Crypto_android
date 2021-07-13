package com.nikodem.crypto.ui.detail

import com.nikodem.crypto.utils.BaseViewModel
import com.nikodem.crypto.utils.ViewState

class CryptoDetailFragmentViewModel : BaseViewModel<CryptoDetailFragmentViewState>(
    initialState = CryptoDetailFragmentViewState()
) {
}

data class CryptoDetailFragmentViewState(
    override val isLoading: Boolean = false
) : ViewState