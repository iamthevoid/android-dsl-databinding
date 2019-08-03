package iam.thevoid.ankoviews.widget.mvvm

import android.view.View
import androidx.lifecycle.ViewModel
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import iam.thevoid.common.view.MvvmDialogFragment

abstract class AnkoMvvmDialogFragment<VM : ViewModel> : MvvmDialogFragment<VM>(), AnkoComponent<AnkoMvvmDialogFragment<VM>> {

    override fun provideContentView(): View? = context?.let { createView(AnkoContext.create(it, this, false)) }

}