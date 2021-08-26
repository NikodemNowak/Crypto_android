package com.nikodem.crypto.ui.settings.changeUsername

import com.nikodem.crypto.repositories.UserRepository
import com.nikodem.crypto.utils.BaseViewModel
import com.nikodem.crypto.utils.ViewState

class ChangeUsernameDialogViewModel(
    private val userRepository: UserRepository
) :
    BaseViewModel<ChangeUsernameDialogViewState>(initialState = ChangeUsernameDialogViewState()) {
    init {
        updateViewState {
            it.copy(
                username = userRepository.username!!
            )
        }
    }

    fun setUsername(username: String) {
        updateViewState { it.copy(username = username) }
    }

    fun saveUsername() {
        userRepository.username = viewState.value!!.username
    }

}

data class ChangeUsernameDialogViewState(
    val username: String = "",
    override val isLoading: Boolean = false
) : ViewState