package com.nikodem.crypto.ui.alert

import com.nikodem.crypto.repositories.CryptoRepository
import com.nikodem.crypto.services.Coin
import com.nikodem.crypto.utils.BaseViewModel
import com.nikodem.crypto.utils.ViewState
import com.nikodem.crypto.utils.fireEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CryptoAlertViewModel(
    private val cryptoRepository: CryptoRepository
) :
    BaseViewModel<CryptoAlertViewState>(initialState = CryptoAlertViewState()) {
    fun setCoinName(coinName: String) {
        updateViewState {
            it.copy(
                coinName = coinName
            )
        }
    }

    fun setCoinUuid(coinUuid: String) {
        updateViewState {
            it.copy(
                coinUuid = coinUuid
            )
        }
    }

    fun getCoins() {
        safeLaunch {
            val cryptoData = withContext(Dispatchers.Main) {
                cryptoRepository.getCryptoData(true)
            }
            updateViewState {
                it.copy(
                    coins = cryptoData.data.coins
                )
            }
        }
    }

    fun getCoinNames(): List<String>? {
        return viewState.value?.coins?.map { coin -> coin.name }
    }

    override fun handleError(exception: Throwable) {
        super.handleError(exception)
        showToastEvent.fireEvent(exception.toString())
    }
}

data class CryptoAlertViewState(
    val coins: List<Coin> = emptyList(),
    val coinName: String = "",
    val coinUuid: String = "",
    override val isLoading: Boolean = false
) : ViewState