package thevoid.iam.ankoobservablecomponents.ui.mvvm.recycler

import iam.thevoid.recycler.DiffCallback
import thevoid.iam.components.mvvm.adapter.ItemBindings
import iam.thevoid.rxe.subscribeSafe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import thevoid.iam.ankoobservablecomponents.data.api.RevolutApi
import thevoid.iam.ankoobservablecomponents.data.api.model.CurrencyRate
import thevoid.iam.ankoobservablecomponents.ui.mvvm.recycler.items.CurrencyHeaderItem
import thevoid.iam.ankoobservablecomponents.ui.mvvm.recycler.items.CurrencySimpleItem
import thevoid.iam.components.mvvm.viewmodel.RxViewModel
import thevoid.iam.components.rx.fields.RxList
import thevoid.iam.components.rx.fields.RxString
import java.util.concurrent.TimeUnit

class RecyclerViewModel : RxViewModel() {

    private val current by lazy { RxString(STARTER) }

    val data by lazy { RxList<Any>() }

    val diffCallback: ((old: List<Any>, new: List<Any>) -> DiffCallback<Any>)? = { old, new ->
        object : DiffCallback<Any>(old, new) {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem::class == newItem::class &&
                    pairOfNonNull<CurrencyRate>(oldItem, newItem)?.let { (old, new) -> old.code == new.code } ?: true

            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = areItemsTheSame(oldItem, newItem) &&
                    pairOfNonNull<CurrencyRate>(
                        oldItem,
                        newItem
                    )?.let { (old, new) -> old.rate == new.rate } ?: pairOfNonNull<String>(
                oldItem,
                newItem
            )?.let { (old, new) -> old == new } ?: false
        }
    }

    val itemBindings = ItemBindings()
        .addBinding(String::class.java) { CurrencyHeaderItem(it) }
        .addBinding(CurrencyRate::class.java) { CurrencySimpleItem(current, it) }

    override fun onActive() {
        super.onActive()
        unsubscribeOnInactive(
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
                .subscribeSafe {
                    data.set(it)
                }
        )
    }

    companion object {
        const val STARTER = "EUR"
    }
}