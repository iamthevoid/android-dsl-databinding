package thevoid.iam.components.mvvm.viewmodel

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


open class RxViewModel : LifecycleTrackingViewModel() {

    private val list by lazy { mutableListOf<Disposable>() }

    private val disposable by lazy { CompositeDisposable() }

    protected fun unsubscribeOnInactive(disposable: Disposable, vararg disposables: Disposable) {
        this.disposable.add(disposable)
        this.list.add(disposable)
        if (disposables.isNotEmpty()) {
            this.disposable.addAll(*disposables)
            disposables.forEach { list.add(it) }
        }
    }

    @CallSuper
    override fun onActive() {
        if (list.isNotEmpty())
            list.forEach { disposable.add(it) }
    }

    @CallSuper
    override fun onInactive() {
        disposable.clear()
    }
}