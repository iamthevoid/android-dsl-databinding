@file:OptIn(ExperimentalStdlibApi::class)

package iam.thevoid.noxml.demo.ui.mvvm.revolut

import androidx.lifecycle.ViewModel
import iam.thevoid.e.format
import iam.thevoid.e.safeDouble
import iam.thevoid.noxml.demo.data.api.RevolutApi
import iam.thevoid.noxml.demo.data.api.model.CurrencyRate
import io.reactivex.processors.BehaviorProcessor
import java.util.concurrent.TimeUnit

class CurrencyViewModel : ViewModel() {

    companion object {
        const val STARTER = "EUR"
        const val DEFAULT_VALUE = "1"
    }

    val loading = BehaviorProcessor.createDefault(false)

    val current by lazy { BehaviorProcessor.createDefault(STARTER) }

    val currentValue = BehaviorProcessor.createDefault(DEFAULT_VALUE)

    val blocking = BehaviorProcessor.createDefault(false)

    val data by lazy {
        current.firstOrError()
            .flatMap { current ->
                RevolutApi.latestRates(current)
                    .map {
                        buildList {
                            add(current)
                            addAll(it.rates)
                        }
                    }
            }
            .repeatWhen { it.delay(1, TimeUnit.SECONDS) }
            .doOnSubscribe { loading.onNext(true) }
            .doOnNext {
                loading.onNext(false)
                blocking.onNext(false)
            }
    }

    fun resultValue(rate : Double, value : String) : String =
        (rate * value.safeDouble()).format(4)

    fun updateCurrent(rate : CurrencyRate) {
        blocking.onNext(true)
        current.onNext(rate.code)
    }
}