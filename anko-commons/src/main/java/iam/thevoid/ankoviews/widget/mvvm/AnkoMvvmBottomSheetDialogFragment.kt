package iam.thevoid.ankoviews.widget.mvvm

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import iam.thevoid.common.view.MvvmBottomSheetDialogFragment
import iam.thevoid.common.view.MvvmDialogFragment

abstract class AnkoMvvmBottomSheetDialogFragment<VM : ViewModel> : MvvmBottomSheetDialogFragment<VM>(), AnkoComponent<Context> {

    override fun provideContentView(): View? = context?.let { createView(AnkoContext.create(it, false)) }

}