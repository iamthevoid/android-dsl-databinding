package thevoid.iam.ankoobservablecomponents.ui.mvvm.page

import android.util.Log
import iam.thevoid.common.adapter.change.textwatcher.OnEditTextChanges
import iam.thevoid.rxe.subscribeSafe
import thevoid.iam.rx.rxdata.fields.RxBoolean
import thevoid.iam.rx.rxdata.fields.RxField
import thevoid.iam.rx.viewmodel.RxViewModel

class PageViewModel : RxViewModel() {

    val changes by lazy { RxField<OnEditTextChanges>() }

    val input by lazy { RxBoolean() }

    override fun onActive() {
        super.onActive()
        input.set(true)
        input.observe().subscribeSafe { Log.d("PageViewModel", "$it") }
    }
}