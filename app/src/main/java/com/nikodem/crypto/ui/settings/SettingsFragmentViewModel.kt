package com.nikodem.crypto.ui.settings

import com.nikodem.crypto.repositories.UserRepository
import com.nikodem.crypto.utils.BaseViewModel
import com.nikodem.crypto.utils.ViewState

class SettingsFragmentViewModel(
    private val userRepository: UserRepository
) :
    BaseViewModel<SettingsFragmentViewState>(
        initialState = SettingsFragmentViewState()
    ) {

    fun darkModeOn() {
        updateViewState {
            it.copy(
                darkMode = true
            )
        }
    }

    fun darkModeOff() {
        updateViewState {
            it.copy(
                darkMode = false
            )
        }
    }

    fun getUsername(): String {
        return userRepository.username!!
    }

}

data class SettingsFragmentViewState(
    val darkMode: Boolean = false,
    override val isLoading: Boolean = false
) : ViewState