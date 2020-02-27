package iam.thevoid.noxml.anko.mvvm

import android.view.View
import androidx.lifecycle.ViewModel
import iam.thevoid.noxml.core.mvvm.view.MvvmDialogFragment
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

abstract class AnkoMvvmDialogFragment<VM : ViewModel> : MvvmDialogFragment<VM>(), AnkoComponent<AnkoMvvmDialogFragment<VM>> {

    override fun provideContentView(): View? = context?.let { createView(AnkoContext.create(it, this, false)) }

}