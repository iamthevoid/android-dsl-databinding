package iam.thevoid.noxml.demo.ui.mvvm.revolut

import androidx.lifecycle.ViewModel
import iam.thevoid.coroutines.repeatFlow
import iam.thevoid.e.format
import iam.thevoid.e.safeDouble
import iam.thevoid.noxml.coroutines.data.CoroutineBoolean
import iam.thevoid.noxml.coroutines.data.CoroutineString
import iam.thevoid.noxml.demo.data.api.RevolutApi
import iam.thevoid.noxml.demo.data.api.model.CurrencyRate
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

class RevolutViewModel : ViewModel() {

    val current by lazy { CoroutineString(STARTER) }

    val currentValue = CoroutineString("1")

    val loading = CoroutineBoolean(true)
    val blocking = CoroutineBoolean()

    @UseExperimental(ExperimentalTime::class)
    val data by lazy {
        loading.set(true)
        repeatFlow(delay = 1000.milliseconds) {
            val items = RevolutApi.latest(current.get()).rates
                .let {
                    mutableListOf<Any>().apply {
                        add(current.get())
                        addAll(it)
                    }
                }
            emit(items)
            loading.set(false)
            blocking.set(false)
        }
    }

    suspend fun resultValue(rate : Double, value : String) : String =
        (rate * value.safeDouble()).format(4)

    fun updateCurrent(rate : CurrencyRate) {
        blocking.set(true)
        current.set(rate.code)
    }

    companion object {
        const val STARTER = "EUR"
    }
}