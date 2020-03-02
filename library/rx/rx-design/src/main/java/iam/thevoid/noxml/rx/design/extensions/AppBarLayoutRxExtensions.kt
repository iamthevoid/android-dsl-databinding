package iam.thevoid.noxml.rx.design.extensions

import com.google.android.material.appbar.AppBarLayout
import iam.thevoid.noxml.design.change.OnOffsetChange
import iam.thevoid.noxml.design.extensions.onOffsetChangedListener
import iam.thevoid.noxml.rx.data.fields.RxField

fun  AppBarLayout.onOffsetChange(onOffsetChange : RxField<OnOffsetChange>) =
    onOffsetChange(onOffsetChange) { it }

fun AppBarLayout.onOffsetChangePercent(onOffsetChange: RxField<Float>) =
    onOffsetChange(onOffsetChange) { it.toPercent() }

fun <T : Any> AppBarLayout.onOffsetChange(onOffsetChange : RxField<T>, mapper : (OnOffsetChange) -> T?) {
    onOffsetChangedListener.addOnOffsetChangeListener(object : iam.thevoid.noxml.design.adapter.OnOffsetChangeListenerAdapter() {
        override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
            onOffsetChange.set(mapper(OnOffsetChange(appBarLayout, verticalOffset)))
        }
    })
}
