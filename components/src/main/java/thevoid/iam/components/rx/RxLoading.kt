package thevoid.iam.components.rx

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import org.reactivestreams.Publisher

class RxLoading<T> : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> =
        upstream
            .doOnSubscribe { subject.onNext(true) }
            .doOnCancel { subject.onNext(false) }
            .doOnError { subject.onNext(false) }
            .doOnNext { subject.onNext(false) }
            .doOnComplete { subject.onNext(false) }

    private val subject: Subject<Boolean> by lazy { BehaviorSubject.createDefault(false) }

    val flowable
        get() = subject.toFlowableLatest()
}