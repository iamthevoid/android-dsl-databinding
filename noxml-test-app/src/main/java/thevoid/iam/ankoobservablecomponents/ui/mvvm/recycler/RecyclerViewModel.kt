package thevoid.iam.ankoobservablecomponents.ui.mvvm.recycler

import androidx.lifecycle.ViewModel
import iam.thevoid.recycler.DiffCallback
import iam.thevoid.rxe.subscribeSafe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import thevoid.iam.ankoobservablecomponents.data.api.RevolutApi
import thevoid.iam.ankoobservablecomponents.data.api.model.CurrencyRate
import thevoid.iam.ankoobservablecomponents.ui.mvvm.recycler.items.CurrencyHeaderItem
import thevoid.iam.ankoobservablecomponents.ui.mvvm.recycler.items.CurrencySimpleItem
import thevoid.iam.rx.adapter.ItemBindings
import thevoid.iam.rx.rxdata.fields.RxList
import thevoid.iam.rx.rxdata.fields.RxString
import thevoid.iam.rx.viewmodel.RxViewModel
import java.util.concurrent.TimeUnit

class RecyclerViewModel : ViewModel() {

    private val current by lazy { RxString(STARTER) }

    val data by lazy {
        Single.defer { RevolutApi.service.latest(current.get()) }
            .subscribeOn(Schedulers.io())
            .map { it.rates }
            .repeatWhen { it.delay(1, TimeUnit.SECONDS) }
            .map {
                mutableListOf<Any>().apply {
                    add(current.get())
                    addAll(it)
                }
            }
    }

    val itemBindings = ItemBindings.of(String::class.java) { CurrencyHeaderItem(it) }
        .addBinding(CurrencyRate::class) { CurrencySimpleItem(current, it) }

    companion object {
        const val STARTER = "EUR"
    }
}