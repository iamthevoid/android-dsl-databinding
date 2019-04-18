package thevoid.iam.components.rx

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import org.reactivestreams.Publisher

class RxFlowableLoading<T> : RxLoading(), FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Publisher<T> =
        upstream
            .doOnSubscribe { subject.onNext(true) }
            .doOnCancel { subject.onNext(false) }
            .doOnError { subject.onNext(false) }
            .doOnNext { subject.onNext(false) }
            .doOnComplete { subject.onNext(false) }

}