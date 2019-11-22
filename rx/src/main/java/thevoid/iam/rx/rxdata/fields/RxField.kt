package thevoid.iam.rx.rxdata.fields

import com.jakewharton.rx.ReplayingShare
import iam.thevoid.rxe.canPublish
import iam.thevoid.util.Optional
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor

class RxField<T>(initial: T? = null, private val onChange: (T?) -> Unit = {}) : RxPropertyChangedCallback {

    override fun onItemChanged() = set(get())

    private val subject by lazy { BehaviorProcessor.createDefault(Optional.of(initial)) }

    fun set(elem: T?) {
        (elem as? RxProperty)?.rxCallback = this
        if (subject.canPublish())
            subject.onNext(Optional.of(elem))
        onChange(elem)
    }

    fun update(onUpdate: T.() -> Unit) = set(get()?.also { it.onUpdate() })

    fun get(): T? = subject.value?.item

    fun observe(): Flowable<Optional<T>> = subject.compose(ReplayingShare.instance())

    fun <O> map(mapper: ((T?) -> O)) = observe().map { mapper(it.item) }

    fun <O> mapSelf(mapper: (T?.() -> O)) = observe().map { it.item.mapper() }

    fun onlyPresent() = observe().filter { it.present }.map { it.item!! }
}