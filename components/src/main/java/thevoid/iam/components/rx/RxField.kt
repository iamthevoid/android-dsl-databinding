package thevoid.iam.components.rx

import iam.thevoid.rxe.canPublish
import iam.thevoid.rxe.toFlowableLatest
import iam.thevoid.util.Optional
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

class RxField<T>(initial : T? = null) {

    private val subject = BehaviorSubject.createDefault(Optional.of(initial))

    fun set(elem: T?) {
        if (subject.canPublish())
            subject.onNext(Optional.of(elem))
    }

    fun get() : T? = subject.value?.elem

    fun observe() : Flowable<Optional<T>> = subject.toFlowableLatest()
}