package iam.thevoid.noxml.rx.design.extensions

import com.google.android.material.appbar.AppBarLayout
import iam.thevoid.noxml.design.change.OnOffsetChange
import iam.thevoid.noxml.design.extensions.onOffsetChangedListener
import iam.thevoid.noxml.rx.rxdata.fields.RxField

fun  AppBarLayout.onOffsetChange(field : RxField<OnOffsetChange>) = onOffsetChange(field) { it }

fun AppBarLayout.onOffsetChangePercent(field: RxField<Float>) =
    onOffsetChange(field) { it.toPercent() }

fun <T : Any> AppBarLayout.onOffsetChange(field : RxField<T>, mapper : (OnOffsetChange) -> T?) {
    onOffsetChangedListener.addOnOffsetChangeListener(object : iam.thevoid.noxml.design.adapter.OnOffsetChangeListenerAdapter() {
        override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
            field.set(mapper(OnOffsetChange(appBarLayout, verticalOffset)))
        }
    })
}
