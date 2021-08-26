package com.nikodem.crypto.ui.main.crypto

import com.hadilq.liveevent.LiveEvent
import com.nikodem.crypto.repositories.CryptoRepository
import com.nikodem.crypto.repositories.UserRepository
import com.nikodem.crypto.services.Coin
import com.nikodem.crypto.utils.BaseViewModel
import com.nikodem.crypto.utils.ViewState
import com.nikodem.crypto.utils.fireEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CryptoFragmentViewModel(
    private val cryptoRepository: CryptoRepository,
    private val userRepository: UserRepository
) :
    BaseViewModel<CryptoFragmentViewState>(initialState = CryptoFragmentViewState()) {

    val refreshingFinishedEvent = LiveEvent<Unit>()

    fun loadCryptoData(useCacheDataIfPossible: Boolean = true) {
        updateViewState {
            it.copy(
                isLoading = true
            )
        }
        safeLaunch {
            val cryptoData = withContext(Dispatchers.IO) {
                cryptoRepository.getCryptoData(useCacheDataIfPossible = useCacheDataIfPossible)
            }

            updateViewState {
                it.copy(
                    coins = cryptoData.data.coins,
                    status = cryptoData.status,
                )
            }

            val favoriteCoins = userRepository.favoriteCoins
            updateViewState {
                it.copy(
                    coins = it.coins.map { coin ->
                        coin.copy(isFavorite = favoriteCoins.contains(coin.name))
                    },
                    isLoading = false
                )
            }

            refreshingFinishedEvent.fireEvent()
        }
    }

    override fun handleError(exception: Throwable) {
        super.handleError(exception)
        showToastEvent.fireEvent(exception.toString())
    }
}

data class CryptoFragmentViewState(
    val status: String? = "loading",
    val coins: List<Coin> = emptyList(),
    override val isLoading: Boolean = false
) : ViewState