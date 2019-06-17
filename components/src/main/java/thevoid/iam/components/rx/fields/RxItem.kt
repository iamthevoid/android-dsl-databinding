package thevoid.iam.components.rx.fields

import android.util.Log
import iam.thevoid.rxe.canPublish
import iam.thevoid.rxe.toFlowableBuffer
import iam.thevoid.rxe.toFlowableLatest
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

open class RxItem<T>(initial : T) {

    private val subject = BehaviorSubject.createDefault(initial)

    fun set(elem: T) {
        if (subject.canPublish())
            subject.onNext(elem)
    }

    fun get() : T = subject.value ?: throw IllegalStateException("Value not provided")

    fun observe() : Flowable<T> = subject.toFlowableLatest()

    fun <E> observe(mapper : T.() -> E) : Flowable<E> = observe().map { it.mapper() }
}

open class RxCharSequence<T : CharSequence>(initial: T = "" as T) : RxItem<T>(initial)

class RxBoolean(initial : Boolean = false)  : RxItem<Boolean>(initial)

class RxFloat(initial : Float = 0f) : RxItem<Float>(initial)

class RxDouble(initial : Double = 0.toDouble())  : RxItem<Double>(initial)

class RxInt(initial : Int = 0) : RxItem<Int>(initial)

class RxLong(initial : Long = 0L) : RxItem<Long>(initial)

class RxString(string: String = "") : RxCharSequence<String>(string)

class RxList<T>(initial : List<T> = emptyList()) : RxItem<List<T>>(initial)


