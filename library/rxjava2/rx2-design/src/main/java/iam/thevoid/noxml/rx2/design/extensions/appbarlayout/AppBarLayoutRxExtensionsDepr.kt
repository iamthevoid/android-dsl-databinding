package iam.thevoid.noxml.rx2.design.extensions.appbarlayout

import com.google.android.material.appbar.AppBarLayout
import iam.thevoid.noxml.design.adapter.OnOffsetChangeListenerAdapter
import iam.thevoid.noxml.design.change.OnOffsetChange
import iam.thevoid.noxml.design.local.extensions.appbarlayout.onOffsetChangedListener
import iam.thevoid.noxml.rx2.data.fields.RxField

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun AppBarLayout.onOffsetChange(onOffsetChange: RxField<OnOffsetChange>) =
    onOffsetChange(onOffsetChange) { it }

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun AppBarLayout.onOffsetChangePercent(onOffsetChange: RxField<Float>) =
    onOffsetChange(onOffsetChange) { it.toPercent() }

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun <T : Any> AppBarLayout.onOffsetChange(onOffsetChange : RxField<T>, mapper : (OnOffsetChange) -> T?) {
    onOffsetChangedListener.addOnOffsetChangeListener(object : OnOffsetChangeListenerAdapter() {
        override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
            onOffsetChange.set(mapper(OnOffsetChange(appBarLayout, verticalOffset)))
        }
    })
}