package iam.thevoid.noxml.demo.ui.mvvm.page

import iam.thevoid.noxml.change.textwatcher.OnEditTextChanges
import iam.thevoid.noxml.rx2.mvvm.viewmodel.RxViewModel
import io.reactivex.processors.BehaviorProcessor

class PageViewModel : RxViewModel() {

    val changes by lazy { BehaviorProcessor.create<OnEditTextChanges>() }

    val input by lazy { BehaviorProcessor.createDefault(true) }
}