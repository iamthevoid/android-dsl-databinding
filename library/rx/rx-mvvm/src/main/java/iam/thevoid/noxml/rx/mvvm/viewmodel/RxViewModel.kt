package iam.thevoid.noxml.rx.mvvm.viewmodel

import androidx.annotation.CallSuper
import iam.thevoid.noxml.core.mvvm.vm.LifecycleTrackingViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class RxViewModel : LifecycleTrackingViewModel() {

    private val onInactiveDisposables by lazy { CompositeDisposable() }
    private val onClearDisposables by lazy { CompositeDisposable() }

    protected fun toDispose(disposable: Disposable): Disposable =
        disposable.also { onInactiveDisposables.add(it) }

    protected fun toDispose(disposable: Disposable?, vararg disposables: Disposable?): Disposable =
        CompositeDisposable().apply {
            disposable?.also { toDispose(it) }?.also { add(it) }
            disposables.mapNotNull { toDispose(it) }.onEach { add(it) }
        }

    protected fun toDisposeOnCleared(disposable: Disposable): Disposable =
        disposable.also { onClearDisposables.add(it) }

    protected fun toDisposeOnCleared(disposable: Disposable?, vararg disposables: Disposable?): Disposable =
        CompositeDisposable().apply {
            disposable?.also { toDisposeOnCleared(it) }?.also { add(it) }
            disposables.mapNotNull { toDisposeOnCleared(it) }.onEach { add(it) }
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