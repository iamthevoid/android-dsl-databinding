package iam.thevoid.noxml.demo.ui.mvvm.revolut

import androidx.lifecycle.ViewModel
import iam.thevoid.coroutines.repeatFlow
import iam.thevoid.e.format
import iam.thevoid.e.safeDouble
import iam.thevoid.noxml.coroutines.data.CoroutineBoolean
import iam.thevoid.noxml.coroutines.data.CoroutineString
import iam.thevoid.noxml.demo.data.api.RevolutApi
import iam.thevoid.noxml.demo.data.api.model.CurrencyRate
import iam.thevoid.noxml.rx2.data.RxLoading
import iam.thevoid.noxml.rx2.utils.loadingUntilNext
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

class CurrencyViewModel : ViewModel() {

    val loading = BehaviorProcessor.createDefault(false)

    val current by lazy { BehaviorProcessor.createDefault(STARTER) }

    val currentValue = BehaviorProcessor.createDefault("1")

    val blocking = BehaviorProcessor.createDefault(false)

    val data by lazy {
        loading.onNext(true)
        Flowable.interval(1, TimeUnit.SECONDS)
            .flatMapSingle { RevolutApi.latest(current.value!!) }
            .map {
                mutableListOf<Any>().apply {
                    add(current.value!!)
                    addAll(it.rates)
                }
            }
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

    companion object {
        const val STARTER = "EUR"
    }
}