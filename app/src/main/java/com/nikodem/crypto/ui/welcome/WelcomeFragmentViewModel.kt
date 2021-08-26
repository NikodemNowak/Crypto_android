package com.nikodem.crypto.ui.welcome

import com.hadilq.liveevent.LiveEvent
import com.nikodem.crypto.repositories.UserRepository
import com.nikodem.crypto.utils.BaseViewModel
import com.nikodem.crypto.utils.ViewState
import com.nikodem.crypto.utils.fireEvent

class WelcomeFragmentViewModel(
    private val userRepository: UserRepository
) :
    BaseViewModel<WelcomeFragmentViewState>(initialState = WelcomeFragmentViewState()) {

    val navigateToCryptoFragment: LiveEvent<Unit> = LiveEvent()

    fun onSubmitButtonClick() {
        val username = viewState.value!!.username
        if (username.length < 3) {
            showToastEvent.fireEvent("Username must have at least 3 characters")
        } else {
            userRepository.username = username
            navigateToCryptoFragment.fireEvent()
        }

    }

    fun setUsername(username: String) {
        updateViewState {
            it.copy(
                username = username
            )
        }
    }

    fun isUsernameAlreadyGiven() {
        if (!userRepository.username.isNullOrEmpty()) {
            navigateToCryptoFragment.fireEvent()
        }
    }
}

data class WelcomeFragmentViewState(
    val username: String = "",
    override val isLoading: Boolean = false
) : ViewState

