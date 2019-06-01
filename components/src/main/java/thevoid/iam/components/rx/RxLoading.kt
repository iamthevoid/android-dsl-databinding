package thevoid.iam.components.rx

import iam.thevoid.e.safe
import iam.thevoid.rxe.toFlowableLatest
import io.reactivex.*
import io.reactivex.subjects.BehaviorSubject
import thevoid.iam.components.rx.fields.RxBoolean

open class RxLoading {
    
    private val loading by lazy { RxBoolean() }

    internal val flowable: Flowable<Boolean>
        get() = loading.observe()

    val now: Boolean
        get() = loading.get()

    fun completable(): CompletableTransformer = CompletableTransformer { upstream ->
        upstream.doOnSubscribe { loading.set(true) }
            .doOnDispose { loading.set(false) }
            .doOnError { loading.set(false) }
            .doOnComplete { loading.set(false) }
            .doOnTerminate { loading.set(false) }
    }

    fun <T> flowable(): FlowableTransformer<T, T> = FlowableTransformer { upstream ->
        upstream.doOnSubscribe { loading.set(true) }
            .doOnCancel { loading.set(false) }
            .doOnError { loading.set(false) }
            .doOnNext { loading.set(false) }
            .doOnComplete { loading.set(false) }
    }

    fun <T> observable(): ObservableTransformer<T, T> = ObservableTransformer { upstream ->
        upstream
            .doOnSubscribe { loading.set(true) }
            .doOnDispose { loading.set(false) }
            .doOnError { loading.set(false) }
            .doOnNext { loading.set(false) }
            .doOnTerminate { loading.set(false) }
    }

    fun <T> single() : SingleTransformer<T,T> = SingleTransformer { upstream ->
        upstream
            .doOnSubscribe { loading.set(true) }
            .doOnDispose { loading.set(false) }
            .doOnError { loading.set(false) }
            .doOnSuccess { loading.set(false) }
            .doOnTerminate { loading.set(false) }
    }
}