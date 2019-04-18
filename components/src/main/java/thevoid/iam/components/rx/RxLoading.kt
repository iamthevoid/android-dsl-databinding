package thevoid.iam.components.rx

import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

abstract class RxLoading {

    protected val subject: Subject<Boolean> by lazy { BehaviorSubject.createDefault(false) }

    val flowable
        get() = subject.toFlowableLatest()
}