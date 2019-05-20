package thevoid.iam.components.rx

import iam.thevoid.e.safe
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

abstract class RxLoading {

    protected val subject: BehaviorSubject<Boolean> by lazy { BehaviorSubject.createDefault(false) }

    val flowable : Flowable<Boolean>
        get() = subject.toFlowableLatest()

    val now : Boolean
        get() = subject.value.safe
}