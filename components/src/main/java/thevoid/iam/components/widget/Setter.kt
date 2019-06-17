package thevoid.iam.components.widget

import android.util.Log
import android.view.View
import iam.thevoid.rxe.subscribeSafe
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

abstract class Setter<V : View, C>(view: V, private val flowable: Flowable<C>) {

    private val viewRef: WeakReference<V> = WeakReference(view)

    private var disposable: Disposable? = null

    abstract fun set(view: V?, component: C)

    val view
        get() = viewRef.get()

    fun subscribeChanges() {
        disposable?.dispose()
        disposable = null
        disposable = flowable
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Log.d("fff", "$it")
            }
            .subscribeSafe { set(viewRef.get(), it) }
    }

    fun unsubscribeChanges() {
        disposable?.dispose()
    }

    fun theSameAs(setter: Setter<*, *>): Boolean =
        setter.flowable === flowable
}