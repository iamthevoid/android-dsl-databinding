package thevoid.iam.components.rx

import io.reactivex.*
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import org.reactivestreams.Publisher

@Deprecated("Use \"single()\" fun instead, will be removed in version above 0.1.11")
class RxSingleLoading<T> : RxLoading(), SingleTransformer<T, T> {
    override fun apply(upstream: Single<T>): SingleSource<T> =
        upstream
            .doOnSubscribe { subject.onNext(true) }
            .doOnDispose { subject.onNext(false) }
            .doOnError { subject.onNext(false) }
            .doOnSuccess { subject.onNext(false) }
            .doOnTerminate { subject.onNext(false) }
}