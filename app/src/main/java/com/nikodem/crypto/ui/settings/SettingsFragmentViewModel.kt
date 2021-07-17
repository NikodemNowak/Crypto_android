package com.nikodem.crypto.ui.settings

import com.nikodem.crypto.utils.BaseViewModel
import com.nikodem.crypto.utils.ViewState

class SettingsFragmentViewModel :
    BaseViewModel<SettingsFragmentViewState>(
        initialState = SettingsFragmentViewState()
    ) {

    fun onThemeChange() {
        updateViewState {
            it.copy(
                darkMode = !it.darkMode
            )
        }
    }

}

data class SettingsFragmentViewState(
    val darkMode: Boolean = false,
    override val isLoading: Boolean = false
) : ViewState