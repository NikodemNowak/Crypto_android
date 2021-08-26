package com.nikodem.crypto.ui.main

import com.nikodem.crypto.utils.BaseViewModel
import com.nikodem.crypto.utils.ViewState

class MainFragmentViewModel :
    BaseViewModel<MainFragmentViewState>(initialState = MainFragmentViewState()) {
}

data class MainFragmentViewState(
    override val isLoading: Boolean = false
) : ViewState