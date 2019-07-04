package thevoid.iam.ankoobservablecomponents.ui.mvvm.page

import android.util.Log
import iam.thevoid.rxe.subscribeSafe
import thevoid.iam.components.mvvm.viewmodel.RxViewModel
import thevoid.iam.components.rx.fields.RxBoolean
import thevoid.iam.components.rx.fields.RxField
import thevoid.iam.components.widget.ext.OnEditTextChanges

class PageViewModel : RxViewModel() {

    val changes by lazy { RxField<OnEditTextChanges>() }

    val input by lazy { RxBoolean() }

    override fun onActive() {
        super.onActive()
        input.set(true)
        input.observe().subscribeSafe { Log.d("PageViewModel", "$it") }
    }
}