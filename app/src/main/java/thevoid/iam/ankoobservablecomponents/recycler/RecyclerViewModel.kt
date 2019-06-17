package thevoid.iam.ankoobservablecomponents.recycler

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import iam.thevoid.ankoviews.widget.adapter.AnkoLayout
import iam.thevoid.e.safe
import iam.thevoid.rxe.subscribeSafe
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*
import thevoid.iam.components.adapter.DiffCallback
import thevoid.iam.components.adapter.RxAdapter
import thevoid.iam.components.mvvm.viewmodel.RxViewModel
import thevoid.iam.components.rx.fields.RxList
import thevoid.iam.components.rx.fields.RxString
import thevoid.iam.components.widget.ext.setText
import java.util.concurrent.TimeUnit

class RecyclerViewModel : RxViewModel() {

    private val current by lazy { RxString(STARTER) }

    val data by lazy { RxList<Any>() }

    val diffCallback: ((old: List<Any>, new: List<Any>) -> DiffCallback<Any>)? = { old, new ->
        object : DiffCallback<Any>(old, new) {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem::class == newItem::class &&
                    pairOfNonNull<CurrencyRate>(oldItem, newItem)?.let { (old, new) -> old.code == new.code } ?: true

            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = areItemsTheSame(oldItem, newItem) &&
                    pairOfNonNull<CurrencyRate>(oldItem, newItem)?.let { (old, new) -> old.rate == new.rate } ?:
                    pairOfNonNull<String>(oldItem, newItem)?.let { (old, new) -> old == new } ?: false
        }
    }

    val itemBindings = RxAdapter.ItemBindings()
        .addBinding(CurrencyRate::class.java) { parent ->
            object : AnkoLayout<CurrencyRate>(parent) {
                override fun createView(ui: AnkoContext<AnkoLayout<CurrencyRate>>): View =
                    ui.frameLayout {
                        textView {
                            padding = dip(8)
                            setText(item.observe { it.elem?.code.safe })
                        }

                        textView {
                            padding = dip(8)
                            setText(item.observe { "${it.elem?.rate.safe}" })
                        }.lparams {
                            gravity = Gravity.END or Gravity.CENTER_VERTICAL
                        }
                    }.apply {
                        layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
                    }
            }
        }
        .addBinding(String::class.java) { parent ->
            object : AnkoLayout<String>(parent) {
                override fun createView(ui: AnkoContext<AnkoLayout<String>>): View =
                    ui.frameLayout {
                        textView {
                            padding = dip(8)
                            textColorResource = android.R.color.black
                            setText(item.observe { it.elem.safe })
                        }
                    }
            }
        }

    override fun onActive() {
        super.onActive()
        unsubscribeOnInactive(
            Api.service.latest(current.get())
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