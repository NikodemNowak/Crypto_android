package com.nikodem.crypto.ui.alert

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.work.*
import com.nikodem.crypto.R
import com.nikodem.crypto.databinding.FragmentCryptoAlertBinding
import com.nikodem.crypto.services.Coin
import com.nikodem.crypto.utils.BaseFragment
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Predicate


class CryptoAlertFragment :
    BaseFragment<CryptoAlertViewState, CryptoAlertViewModel, FragmentCryptoAlertBinding>(
        R.layout.fragment_crypto_alert,
        CryptoAlertViewModel::class
    ) {

    private val args: CryptoAlertFragmentArgs by navArgs()
    private val coinName: String by lazy { args.coinName }
    private val coinUuid: String by lazy { args.coinUuid }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setCoinName(coinName)
        viewModel.setCoinUuid(coinUuid)

        viewModel.getCoins()

        val coins = viewModel.viewState.value?.coins

        val coinNames: List<String>? = viewModel.getCoinNames()

        val spinner = binding.alertSpinnerCoinName

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            coinNames!!
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val initialCoinPosition: Int = coins?.indexOf(findCoin(coins, coinName).get()) ?: 0

        spinner.adapter = adapter

        spinner.setSelection(initialCoinPosition)

        binding.setAlertButton.setOnClickListener {
            val startTargetValue = binding.startTargetValue
            val endTargetValue = binding.endTargetValue
            val chosenCoinName = binding.alertSpinnerCoinName.selectedItem.toString()
            val coin: Optional<Coin> = findCoin(coins!!, chosenCoinName)
            val chosenCoinUuid: String = coin.get().uuid

            Timber.d(coin.toString())
//            listCarnet.stream().filter(carnet -> codeIsIn.equals(carnet.getCodeIsin())).findFirst().orElse(null);

            if (!startTargetValue.text.isNullOrEmpty()) {
                if (!endTargetValue.text.isNullOrEmpty()) {
                    val constraints = Constraints.Builder()
                        .setRequiresCharging(false)
                        .build()

                    val data = Data.Builder()
                        .putString("COIN_UUID", chosenCoinUuid)
                        .putDouble(
                            "COIN_PRICE_START",
                            binding.startTargetValue.text.toString().toDouble()
                        ).putDouble(
                            "COIN_PRICE_END",
                            binding.endTargetValue.text.toString().toDouble()
                        )
                        .putString(
                            "COIN_NAME",
                            chosenCoinName
                        )
                        .putString("ALERT_NAME", binding.alertName.text.toString())
                        .build()

                    val builder = PeriodicWorkRequest.Builder(
                        CryptoWorker::class.java,
                        30,
                        TimeUnit.MINUTES,
                        15,
                        TimeUnit.MINUTES
                    ).setInputData(data).build()

                    val notificationWork = OneTimeWorkRequest.Builder(CryptoWorker::class.java)
                        .setInitialDelay(5000, TimeUnit.MILLISECONDS).setInputData(data).build()

                    val workManager = WorkManager.getInstance(requireContext())

//            workManager.beginUniqueWork("name", ExistingWorkPolicy.KEEP, notificationWork).enqueue()

                    workManager.enqueueUniquePeriodicWork(
                        CRYPTO_WORKER,
                        ExistingPeriodicWorkPolicy.KEEP,
                        builder
                    )

                    Toast.makeText(requireContext(), "Alert set!", Toast.LENGTH_SHORT).show()

                    hideKeyboard(requireView())
                } else {
                    endTargetValue.error = "End target value cannot be empty"
                }

            } else {
                startTargetValue.error = "Start target value cannot be empty"

            }

        }
    }

    companion object {
        const val CRYPTO_WORKER = "CRYPTO_WORKER"
    }

    private fun hideKeyboard(v: View) {
        (requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?)?.hideSoftInputFromWindow(
            v.applicationWindowToken,
            0
        )
    }

    private fun findCoin(list: List<Coin>, chosen: String): Optional<Coin> {
        return list.stream().filter(Predicate<Coin> { coin: Coin ->
            coin.name == chosen
        }).findAny()
    }
}