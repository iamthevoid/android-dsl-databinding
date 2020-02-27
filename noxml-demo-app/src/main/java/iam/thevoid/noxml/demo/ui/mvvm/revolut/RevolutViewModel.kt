package iam.thevoid.noxml.demo.ui.mvvm.revolut

import androidx.lifecycle.ViewModel
import iam.thevoid.e.format
import iam.thevoid.e.safeDouble
import iam.thevoid.noxml.demo.data.api.RevolutApi
import iam.thevoid.noxml.demo.data.api.model.CurrencyRate
import iam.thevoid.noxml.coroutines.fields.CoroutineBoolean
import iam.thevoid.noxml.coroutines.fields.CoroutineString
import iam.thevoid.noxml.coroutines.repeatFlow
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

class RevolutViewModel : ViewModel() {

    val current by lazy { CoroutineString(STARTER) }

    val currentValue by lazy { CoroutineString("1") }

    val loading : CoroutineBoolean = CoroutineBoolean(true)
    val blocking : CoroutineBoolean = CoroutineBoolean()

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

    suspend fun resultValue(rate : Double, value : String) : String{
        val opRate = rate
        val opValue = value.safeDouble()
        return (opRate * opValue).format(4)
    }

    fun updateCurrent(rate : CurrencyRate) {
        blocking.set(true)
        current.set(rate.code)
    }

    companion object {
        const val STARTER = "EUR"
    }
}