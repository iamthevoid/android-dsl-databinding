package thevoid.iam.components.mvvm.viewmodel

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


open class MvvmViewModel : LifecycleTrackingViewModel() {
    override fun onActive() = Unit
    override fun onInactive() = Unit
}