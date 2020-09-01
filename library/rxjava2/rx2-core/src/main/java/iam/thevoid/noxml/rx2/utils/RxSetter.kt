package iam.thevoid.noxml.rx2.utils

import android.view.View
import iam.thevoid.noxml.core.local.binding.Setter
import iam.thevoid.rxe.subscribeSafe
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

abstract class RxSetter<V : View, C>(
    view: V,
    private val flowable: Flowable<C>
) : Setter<V, C>(view){

    private var disposable: Disposable? = null

    override fun applyFirstChangeBlocking() {
        set(view, flowable.blockingLatest().first())
    }

    override fun subscribeChanges() {
        disposable?.dispose()
        disposable = null
        disposable = flowable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeSafe { set(viewRef.get(), it) }
    }

    override fun unsubscribeChanges() {
        disposable?.dispose()
    }

    override fun theSameAs(setter: Setter<*, *>): Boolean =
        setter is RxSetter && setter.flowable === flowable
}