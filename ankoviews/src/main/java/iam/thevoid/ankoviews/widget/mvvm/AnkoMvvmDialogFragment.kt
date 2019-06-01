package iam.thevoid.ankoviews.widget.mvvm

import android.view.View
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import thevoid.iam.components.mvvm.view.MvvmDialogFragment

abstract class AnkoMvvmDialogFragment : MvvmDialogFragment(), AnkoComponent<AnkoMvvmDialogFragment> {

    override fun provideContentView(): View? = context?.let { createView(AnkoContext.create(it, this, false)) }

}