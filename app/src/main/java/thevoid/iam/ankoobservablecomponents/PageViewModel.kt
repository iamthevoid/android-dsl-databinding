package thevoid.iam.ankoobservablecomponents

import thevoid.iam.components.mvvm.viewmodel.RxViewModel
import thevoid.iam.components.rx.fields.RxField
import thevoid.iam.components.widget.ext.OnEditTextChanges

class PageViewModel : RxViewModel() {

    val changes by lazy { RxField<OnEditTextChanges>() }

}