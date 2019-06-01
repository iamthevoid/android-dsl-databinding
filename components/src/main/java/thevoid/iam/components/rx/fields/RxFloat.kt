package thevoid.iam.components.rx.fields

import iam.thevoid.e.safe
import iam.thevoid.rxe.canPublish
import iam.thevoid.rxe.toFlowableLatest
import iam.thevoid.util.Optional
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

class RxFloat(initial : Float = 0f) {

    private val subject = BehaviorSubject.createDefault(initial)

    fun set(elem: Float) {
        if (subject.canPublish())
            subject.onNext(elem)
    }

    fun get() : Float = subject.value.safe

    fun <E> mapper(mapper : Float.() -> E) : Flowable<E> = observe().map { it.mapper() }

    fun observe() : Flowable<Float> = subject.toFlowableLatest()
}