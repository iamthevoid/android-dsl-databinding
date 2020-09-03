@file:Suppress("unused")

package iam.thevoid.noxml.rx2.design.extensions.appbarlayout

import com.google.android.material.appbar.AppBarLayout
import iam.thevoid.noxml.design.adapter.OnOffsetChangeListenerAdapter
import iam.thevoid.noxml.design.change.OnOffsetChange
import iam.thevoid.noxml.design.local.extensions.appbarlayout.onOffsetChangedListener
import iam.thevoid.noxml.rx2.data.onlyPresent
import iam.thevoid.noxml.rx2.extensions.view.addSetter
import iam.thevoid.util.Optional
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor

/**
 * onOffsetChange
 */

fun AppBarLayout.onOffsetChangePercent(
    onOffsetChange: FlowableProcessor<Float>,
    unwrap: Flowable<Optional<Float>>.() -> Flowable<Float> = { onlyPresent() }
) = onOffsetChange(onOffsetChange, mapper = { it.toPercent() }, unwrap)

fun AppBarLayout.onOffsetChange(
    onOffsetChange: FlowableProcessor<OnOffsetChange>,
    unwrap: Flowable<Optional<OnOffsetChange>>.() -> Flowable<OnOffsetChange> = { onlyPresent() }
) = onOffsetChange(onOffsetChange, mapper = { it }, unwrap)

fun <T : Any> AppBarLayout.onOffsetChange(
    onOffsetChange: FlowableProcessor<T>,
    mapper: (OnOffsetChange) -> T?,
    unwrap: Flowable<Optional<T>>.() -> Flowable<T> = { onlyPresent() }
) =
    addSetter(Flowable.create<Optional<T>>({
        val listener = object : OnOffsetChangeListenerAdapter() {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                it.onNext(Optional.of(mapper(OnOffsetChange(appBarLayout, verticalOffset))))
            }
        }
        onOffsetChangedListener.addOnOffsetChangeListener(listener)
        it.setCancellable { onOffsetChangedListener.removeOnOffsetChangeListener(listener) }
    }, BackpressureStrategy.LATEST).unwrap().doOnNext(onOffsetChange::onNext))
