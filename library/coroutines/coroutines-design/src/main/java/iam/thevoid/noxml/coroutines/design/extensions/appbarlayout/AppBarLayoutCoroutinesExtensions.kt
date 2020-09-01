package iam.thevoid.noxml.coroutines.design.extensions.appbarlayout

import com.google.android.material.appbar.AppBarLayout
import iam.thevoid.noxml.coroutines.data.CoroutineField
import iam.thevoid.noxml.design.adapter.OnOffsetChangeListenerAdapter
import iam.thevoid.noxml.design.change.OnOffsetChange
import iam.thevoid.noxml.design.local.extensions.appbarlayout.onOffsetChangedListener

fun AppBarLayout.onOffsetChange(onOffsetChange: CoroutineField<OnOffsetChange>) =
    onOffsetChange(onOffsetChange) { it }

fun AppBarLayout.onOffsetChangePercent(onOffsetChange: CoroutineField<Float>) =
    onOffsetChange(onOffsetChange) { it.toPercent() }

fun <T : Any> AppBarLayout.onOffsetChange(onOffsetChange: CoroutineField<T>, mapper: (OnOffsetChange) -> T) {
    onOffsetChangedListener.addOnOffsetChangeListener(object : OnOffsetChangeListenerAdapter() {
        override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
            onOffsetChange.set(mapper(OnOffsetChange(appBarLayout, verticalOffset)))
        }
    })
}
