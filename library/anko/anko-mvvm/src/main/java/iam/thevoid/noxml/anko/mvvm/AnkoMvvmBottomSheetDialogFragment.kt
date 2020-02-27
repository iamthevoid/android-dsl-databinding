package iam.thevoid.noxml.anko.mvvm

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import iam.thevoid.noxml.design.view.MvvmBottomSheetDialogFragment
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

abstract class AnkoMvvmBottomSheetDialogFragment<VM : ViewModel> : MvvmBottomSheetDialogFragment<VM>(), AnkoComponent<Context> {

    override fun provideContentView(): View? = context?.let { createView(AnkoContext.create(it, false)) }

}