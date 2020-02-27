package iam.thevoid.noxml.anko.mvvm

import android.view.View
import androidx.lifecycle.ViewModel
import iam.thevoid.noxml.core.mvvm.view.MvvmFragment
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

abstract class AnkoMvvmFragment<VM: ViewModel> : MvvmFragment<VM>(), AnkoComponent<AnkoMvvmFragment<VM>> {

    override fun provideContentView(): View? = context?.let { createView(AnkoContext.create(it, this, false)) }

}