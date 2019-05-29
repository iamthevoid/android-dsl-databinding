package thevoid.iam.components.rx.fields

import iam.thevoid.e.safe
import iam.thevoid.rxe.canPublish
import iam.thevoid.rxe.toFlowableLatest
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

class RxString(string: String = "") : RxCharSequence<String>(string)