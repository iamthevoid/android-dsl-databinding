package thevoid.iam.components.rx

import io.reactivex.*
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import org.reactivestreams.Publisher

class RxCompletableLoading : RxLoading(), CompletableTransformer {

    override fun apply(upstream: Completable): CompletableSource =
        upstream
            .doOnSubscribe { subject.onNext(true) }
            .doOnDispose { subject.onNext(false) }
            .doOnError { subject.onNext(false) }
            .doOnComplete { subject.onNext(false) }
            .doOnTerminate { subject.onNext(false) }

}