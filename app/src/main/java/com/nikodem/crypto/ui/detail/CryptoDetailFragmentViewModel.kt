package com.nikodem.crypto.ui.detail

import com.nikodem.crypto.repositories.CryptoRepository
import com.nikodem.crypto.repositories.UserRepository
import com.nikodem.crypto.services.Coin
import com.nikodem.crypto.utils.BaseViewModel
import com.nikodem.crypto.utils.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CryptoDetailFragmentViewModel(
    private val cryptoRepository: CryptoRepository,
    private val userRepository: UserRepository
) : BaseViewModel<CryptoDetailFragmentViewState>(
    initialState = CryptoDetailFragmentViewState()
) {
    fun setCoin(coin: Coin) {
        updateViewState {
            it.copy(
                name = coin.name,
                uuid = coin.uuid,
                symbol = coin.symbol,
                iconUrl = coin.iconUrl,
                price = coin.price,
                marketCap = coin.marketCap,
                change = coin.change,
                isLoading = true
            )
        }
    }

    fun loadDetailCryptoData(uuid: String) {
        updateViewState { it.copy(isDetailsLoading = true) }
        safeLaunch {
            val cryptoDetailData = withContext(Dispatchers.IO) {
                cryptoRepository.getDetailCryptoData(uuid)
            }
            updateViewState {
                it.copy(
                    description = cryptoDetailData.data.coin.description!!,
                    rank = cryptoDetailData.data.coin.rank.toString(),
                    numberOfMarkets = cryptoDetailData.data.coin.numberOfMarkets.toString(),
                    isDetailsLoading = false,
                    isButtonVisible = false
                )
            }
        }
        safeLaunch {
            val cryptoSupplyData = withContext(Dispatchers.IO) {
                cryptoRepository.getSupplyCryptoData(uuid)
            }
            updateViewState {
                it.copy(
                    maxAmount = cryptoSupplyData.data.supply.maxAmount
                )
            }
            if (viewState.value!!.maxAmount.isNullOrEmpty()) {
                updateViewState {
                    it.copy(
                        isSupplySectionVisible = false
                    )
                }
            }
        }
    }

    override fun handleError(exception: Throwable) {
        super.handleError(exception)
        updateViewState {
            it.copy(
                isButtonVisible = true
            )
        }
    }

    fun getFavoriteCoins(): Set<String> {
        return userRepository.favoriteCoins
    }

    fun isCoinFavorite(coinName: String): Boolean {
        if (userRepository.favoriteCoins.contains(coinName)) {
            return true
        }
        return false
    }

    fun changeFavoriteCoin(coinName: String): Boolean {
        val set = userRepository.favoriteCoins
        return if (set.contains(coinName)) {
            set.remove(coinName)
            userRepository.favoriteCoins = set
            false
        } else {
            set.add(coinName)
            userRepository.favoriteCoins = set
            true
        }
    }
}

data class CryptoDetailFragmentViewState(
    val name: String = "",
    val uuid: String = "",
    val symbol: String = "",
    val iconUrl: String? = "",
    val price: String = "",
    val marketCap: String = "",
    val change: String = "",
    val description: String = "",
    val rank: String = "",
    val numberOfMarkets: String = "",
    val maxAmount: String? = "",
    val isButtonVisible: Boolean = true,
    val isSupplySectionVisible: Boolean = true,
    val isDetailsLoading: Boolean = false,
    override val isLoading: Boolean = false
) : ViewState