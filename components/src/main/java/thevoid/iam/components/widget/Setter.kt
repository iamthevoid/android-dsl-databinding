package thevoid.iam.components.widget

import android.view.View
import iam.thevoid.rxe.subscribeSafe
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

abstract class Setter<V : View, C>(view: V, private val observable: Flowable<C>) {

    private val viewRef: WeakReference<V> = WeakReference(view)

    private var disposable: Disposable? = null

    abstract fun set(view: V?, component: C)

    fun subscribeChanges() {
        disposable?.dispose()
        disposable = null
        disposable = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeSafe { set(viewRef.get(), it) }
    }

    fun unsubscribeChanges() {
        disposable?.dispose()
    }

    fun theSameAs(setter: Setter<*, *>): Boolean =
        setter.observable === observable
}