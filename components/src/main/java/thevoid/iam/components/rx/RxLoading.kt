package thevoid.iam.components.rx

import iam.thevoid.e.safe
import io.reactivex.*
import io.reactivex.subjects.BehaviorSubject

open class RxLoading {

    protected val subject: BehaviorSubject<Boolean> by lazy { BehaviorSubject.createDefault(false) }

    val flowable: Flowable<Boolean>
        get() = subject.toFlowableLatest()

    val now: Boolean
        get() = subject.value.safe

    fun completable(): CompletableTransformer = CompletableTransformer { upstream ->
        upstream.doOnSubscribe { subject.onNext(true) }
            .doOnDispose { subject.onNext(false) }
            .doOnError { subject.onNext(false) }
            .doOnComplete { subject.onNext(false) }
            .doOnTerminate { subject.onNext(false) }
    }

    fun <T> flowable(): FlowableTransformer<T, T> = FlowableTransformer { upstream ->
        upstream.doOnSubscribe { subject.onNext(true) }
            .doOnCancel { subject.onNext(false) }
            .doOnError { subject.onNext(false) }
            .doOnNext { subject.onNext(false) }
            .doOnComplete { subject.onNext(false) }
    }

    fun <T> observable(): ObservableTransformer<T, T> = ObservableTransformer { upstream ->
        upstream
            .doOnSubscribe { subject.onNext(true) }
            .doOnDispose { subject.onNext(false) }
            .doOnError { subject.onNext(false) }
            .doOnNext { subject.onNext(false) }
            .doOnTerminate { subject.onNext(false) }
    }

    fun <T> single() : SingleTransformer<T,T> = SingleTransformer { upstream ->
        upstream
            .doOnSubscribe { subject.onNext(true) }
            .doOnDispose { subject.onNext(false) }
            .doOnError { subject.onNext(false) }
            .doOnSuccess { subject.onNext(false) }
            .doOnTerminate { subject.onNext(false) }
    }
}