package thevoid.iam.components.rx.fields

import iam.thevoid.e.safe
import iam.thevoid.rxe.canPublish
import iam.thevoid.rxe.toFlowableLatest
import iam.thevoid.util.Optional
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

class RxLong(initial : Long = 0L) {

    private val subject = BehaviorSubject.createDefault(initial)

    fun set(elem: Long) {
        if (subject.canPublish())
            subject.onNext(elem)
    }

    fun get() : Long = subject.value.safe

    fun observe() : Flowable<Long> = subject.toFlowableLatest()
}