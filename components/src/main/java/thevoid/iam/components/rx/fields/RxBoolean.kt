package thevoid.iam.components.rx.fields

import iam.thevoid.e.safe
import iam.thevoid.rxe.canPublish
import iam.thevoid.rxe.toFlowableLatest
import iam.thevoid.util.Optional
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

class RxBoolean(initial : Boolean = false) {

    private val subject = BehaviorSubject.createDefault(initial)

    fun set(elem: Boolean) {
        if (subject.canPublish())
            subject.onNext(elem)
    }

    fun get() : Boolean = subject.value.safe

    fun <E> mapper(mapper : Boolean.() -> E) : Flowable<E> = observe().map { it.mapper() }

    fun observe() : Flowable<Boolean> = subject.toFlowableLatest()
}