package thevoid.iam.components.mvvm.viewmodel

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


open class RxViewModel : LifecycleTrackingViewModel() {

    private val onInactiveDisposables by lazy { CompositeDisposable() }
    private val onClearDisposables by lazy { CompositeDisposable() }

    protected fun toDispose(disposable: Disposable?, vararg disposables: Disposable?) {
        disposable?.also { onInactiveDisposables.add(it) }
        disposables.forEach { it?.also { disp -> onInactiveDisposables.add(disp) } }
    }

    protected fun toDisposeOnCleared(disposable: Disposable?, vararg disposables: Disposable?) {
        disposable?.also { onClearDisposables.add(it) }
        disposables.forEach { it?.also { disp -> onClearDisposables.add(disp) } }
    }

    override fun onActive() {
    }

    @CallSuper
    override fun onInactive() {
        onInactiveDisposables.clear()
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        onClearDisposables.clear()
    }
}