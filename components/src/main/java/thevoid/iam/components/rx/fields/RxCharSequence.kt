package thevoid.iam.components.rx.fields

import iam.thevoid.e.safe
import iam.thevoid.rxe.canPublish
import iam.thevoid.rxe.toFlowableLatest
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

open class RxCharSequence<T : CharSequence>(initial : T = "" as T) {

    private val subject = BehaviorSubject.createDefault(initial)

    fun set(string: T) {
        if (subject.canPublish())
            subject.onNext(string)
    }

    fun get() : T = subject.value.safe

    fun <E> mapper(mapper : T.() -> E) : Flowable<E> = observe().map { it.mapper() }

    fun observe() : Flowable<T> = subject.toFlowableLatest()
}