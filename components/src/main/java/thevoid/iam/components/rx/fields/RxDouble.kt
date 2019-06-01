package thevoid.iam.components.rx.fields

import iam.thevoid.e.safe
import iam.thevoid.rxe.canPublish
import iam.thevoid.rxe.toFlowableLatest
import iam.thevoid.util.Optional
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

class RxDouble(initial : Double = 0.toDouble()) {

    private val subject = BehaviorSubject.createDefault(initial)

    fun set(elem: Double) {
        if (subject.canPublish())
            subject.onNext(elem)
    }

    fun get() : Double = subject.value.safe

    fun <E> mapper(mapper : Double.() -> E) : Flowable<E> = observe().map { it.mapper() }

    fun observe() : Flowable<Double> = subject.toFlowableLatest()
}