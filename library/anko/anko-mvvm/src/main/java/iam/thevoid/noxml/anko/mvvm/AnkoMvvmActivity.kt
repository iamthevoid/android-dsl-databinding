package iam.thevoid.noxml.anko.mvvm

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import iam.thevoid.noxml.core.mvvm.view.MvvmActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

abstract class AnkoMvvmActivity<VM : ViewModel> : MvvmActivity<VM>(), AnkoComponent<Context> {

    override fun provideContentView(): View? = createView(AnkoContext.create(this, this))

}