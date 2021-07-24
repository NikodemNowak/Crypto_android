package com.nikodem.crypto.ui.main

import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.nikodem.crypto.repositories.CryptoRepository
import com.nikodem.crypto.services.Coin
import com.nikodem.crypto.utils.BaseViewModel
import com.nikodem.crypto.utils.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CryptoFragmentViewModel(
    private val cryptoRepository: CryptoRepository
) :
    BaseViewModel<CryptoFragmentViewState>(initialState = CryptoFragmentViewState()) {

    val refreshingFinishedEvent = LiveEvent<Unit>()

    fun loadCryptoData(useCacheDataIfPossible: Boolean = true) {
        updateViewState {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            val cryptoData = withContext(Dispatchers.IO) {
                cryptoRepository.getCryptoData(useCacheDataIfPossible = useCacheDataIfPossible)
            }

            updateViewState {
                it.copy(
                    coins = cryptoData.data.coins,
                    status = cryptoData.status,
                    isLoading = false
                )
            }
            refreshingFinishedEvent.value = Unit
        }
    }
}

data class CryptoFragmentViewState(
    val status: String? = "loading",
    val coins: List<Coin> = emptyList(),
    override val isLoading: Boolean = false
) : ViewState