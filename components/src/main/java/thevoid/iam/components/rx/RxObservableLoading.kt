package thevoid.iam.components.rx

import io.reactivex.*
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import org.reactivestreams.Publisher

class RxObservableLoading<T> : RxLoading(), ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> =
        upstream
            .doOnSubscribe { subject.onNext(true) }
            .doOnDispose { subject.onNext(false) }
            .doOnError { subject.onNext(false) }
            .doOnNext { subject.onNext(false) }
            .doOnTerminate { subject.onNext(false) }

}