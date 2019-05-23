package thevoid.iam.components.rx

import iam.thevoid.e.safe
import iam.thevoid.rxe.canPublish
import iam.thevoid.rxe.toFlowableLatest
import iam.thevoid.util.Optional
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

class RxInt(initial : Int = 0) {

    private val subject = BehaviorSubject.createDefault(initial)

    fun set(elem: Int) {
        if (subject.canPublish())
            subject.onNext(elem)
    }

    fun get() : Int = subject.value.safe

    fun observe() : Flowable<Int> = subject.toFlowableLatest()
}