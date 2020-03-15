package iam.thevoid.noxml.demo.ui.mvvm.page

import iam.thevoid.noxml.change.textwatcher.OnEditTextChanges
import iam.thevoid.noxml.rx2.mvvm.viewmodel.RxViewModel
import iam.thevoid.noxml.rx2.data.fields.RxBoolean
import iam.thevoid.noxml.rx2.data.fields.RxField

class PageViewModel : RxViewModel() {

    val changes by lazy { RxField<OnEditTextChanges>() }

    val input by lazy { RxBoolean(true) }
}