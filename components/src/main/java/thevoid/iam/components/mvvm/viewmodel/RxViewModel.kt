package thevoid.iam.components.mvvm.viewmodel

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


open class RxViewModel : LifecycleTrackingViewModel() {

    private val disposable by lazy { CompositeDisposable() }
    private val onClearDisposables by lazy { CompositeDisposable() }

    protected fun toDispose(disposable: Disposable, vararg disposables: Disposable) {
        this.disposable.add(disposable)
        if (disposables.isNotEmpty())
            this.disposable.addAll(*disposables)
    }

    protected fun toDisposeOnCleared(disposable: Disposable, vararg disposables: Disposable) {
        this.onClearDisposables.add(disposable)
        if (disposables.isNotEmpty())
            this.onClearDisposables.addAll(*disposables)
    }

    override fun onActive() {
    }

    @CallSuper
    override fun onInactive() {
        disposable.clear()
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        onClearDisposables.clear()
    }
}