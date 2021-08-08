package iam.thevoid.noxml.rx2.mvvm.viewmodel

import androidx.annotation.CallSuper
import iam.thevoid.noxml.core.mvvm.vm.LifecycleTrackingViewModel
import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscription

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

    protected fun toDisposeOnCleared(
        disposable: Disposable?,
        vararg disposables: Disposable?
    ): Disposable =
        CompositeDisposable().apply {
            disposable?.also { toDisposeOnCleared(it) }?.also { add(it) }
            disposables.mapNotNull { toDisposeOnCleared(it) }.onEach { add(it) }
        }

    protected fun <T> Flowable<T>.toDispose(
        onNext: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onComplete: () -> Unit = {},
        onSubscribe: (Subscription) -> Unit = {}
    ) {
        toDispose(subscribe(onNext, onError, onComplete, onSubscribe))
    }

    protected fun <T> Flowable<T>.toDisposeOnCleared(
        onNext: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onComplete: () -> Unit = {},
        onSubscribe: (Subscription) -> Unit = {}
    ) {
        toDisposeOnCleared(subscribe(onNext, onError, onComplete, onSubscribe))
    }

    protected fun <T> Observable<T>.toDispose(
        onNext: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onComplete: () -> Unit = {},
        onSubscribe: (Disposable) -> Unit = {}
    ) {
        toDispose(subscribe(onNext, onError, onComplete, onSubscribe))
    }

    protected fun <T> Observable<T>.toDisposeOnCleared(
        onNext: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onComplete: () -> Unit = {},
        onSubscribe: (Disposable) -> Unit = {}
    ) {
        toDisposeOnCleared(subscribe(onNext, onError, onComplete, onSubscribe))
    }

    protected fun <T> Single<T>.toDispose(
        onSuccess: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        toDispose(subscribe(onSuccess, onError))
    }

    protected fun <T> Single<T>.toDisposeOnCleared(
        onSuccess: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        toDisposeOnCleared(subscribe(onSuccess, onError))
    }

    protected fun <T> Maybe<T>.toDispose(
        onSuccess: (T) -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        toDispose(subscribe(onSuccess, onError, onComplete))
    }

    protected fun <T> Maybe<T>.toDisposeOnCleared(
        onSuccess: (T) -> Unit = {},
        onComplete: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        toDisposeOnCleared(subscribe(onSuccess, onError, onComplete))
    }

    protected fun Completable.toDispose(
        onComplete: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        toDispose(subscribe(onComplete, onError))
    }

    protected fun Completable.toDisposeOnCleared(
        onComplete: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        toDisposeOnCleared(subscribe(onComplete, onError))
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