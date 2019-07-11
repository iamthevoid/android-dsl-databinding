package iam.thevoid.design.delegate

import com.google.android.material.appbar.AppBarLayout
import iam.thevoid.design.OnOffsetChangeListenerDelegate
import iam.thevoid.design.R
import iam.thevoid.design.adapter.OnOffsetChangeListenerAdapter
import thevoid.iam.components.rx.fields.RxField
import thevoid.iam.components.widget.ext.addGetter

private val AppBarLayout.onOffsetChangedListener
    get() = (getTag(R.id.onOffsetChangedListenerDelegate) as? OnOffsetChangeListenerDelegate) ?:
            OnOffsetChangeListenerDelegate().also {
                setTag(R.id.onOffsetChangedListenerDelegate, this)
                addOnOffsetChangedListener(it)
            }

fun  AppBarLayout.onOffsetChange(field : RxField<OnOffsetChange>) = onOffsetChange(field) { it }

fun <T : Any> AppBarLayout.onOffsetChange(field : RxField<T>, mapper : (OnOffsetChange) -> T?) =
    addGetter({
        onOffsetChangedListener.addOnOffsetChangeListener(object : OnOffsetChangeListenerAdapter() {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                it.invoke(mapper(OnOffsetChange(appBarLayout, verticalOffset)))
            }
        })
    }, field)


data class OnOffsetChange(val appBarLayout: AppBarLayout?, val verticalOffset : Int)