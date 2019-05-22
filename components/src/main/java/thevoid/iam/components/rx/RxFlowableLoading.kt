package thevoid.iam.components.rx

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import org.reactivestreams.Publisher

@Deprecated("Use \"flowable()\" fun instead, will be removed in version above 0.1.11")
class RxFlowableLoading : RxLoading(), FlowableTransformer<Any, Any> {

    override fun apply(upstream: Flowable<Any>): Publisher<Any> =
        upstream
            .doOnSubscribe { subject.onNext(true) }
            .doOnCancel { subject.onNext(false) }
            .doOnError { subject.onNext(false) }
            .doOnNext { subject.onNext(false) }
            .doOnComplete { subject.onNext(false) }

}