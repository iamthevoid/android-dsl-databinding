package thevoid.iam.components.rx.fields

import iam.thevoid.rxe.canPublish
import iam.thevoid.rxe.toFlowableLatest
import iam.thevoid.util.Optional
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

class RxField<T>(initial: T? = null, private val onChange: (T?) -> Unit = {}) : RxPropertyChangedCallback {

    override fun onItemChanged() = set(get())

    private val subject: BehaviorSubject<Optional<T>> = BehaviorSubject.createDefault(Optional.of(initial))

    fun set(elem: T?) {
        (elem as? RxProperty)?.rxCallback = this
        if (subject.canPublish())
            subject.onNext(Optional.of(elem))
        onChange(elem)
    }

    fun get(): T? = subject.value?.elem

    fun observe(): Flowable<Optional<T>> = subject.toFlowableLatest()

    fun <O> observe(mapper: ((Optional<T>) -> O)) = observe().map { mapper(it) }
}