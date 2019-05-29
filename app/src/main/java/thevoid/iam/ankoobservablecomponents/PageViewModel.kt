package thevoid.iam.ankoobservablecomponents

import iam.thevoid.rxe.toFlowableLatest
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import thevoid.iam.components.mvvm.viewmodel.RxViewModel
import thevoid.iam.components.rx.fields.RxField
import thevoid.iam.components.rx.fields.RxString
import thevoid.iam.components.widget.ext.OnEditTextChanges
import java.util.concurrent.TimeUnit

class PageViewModel : RxViewModel() {

    val changes by lazy { RxField<OnEditTextChanges>() }

}