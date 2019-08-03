package iam.thevoid.ankoviews.widget.mvvm

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import iam.thevoid.common.view.MvvmActivity

abstract class AnkoMvvmActivity<VM : ViewModel> : MvvmActivity<VM>(), AnkoComponent<Context> {

    override fun provideContentView(): View? = createView(AnkoContext.create(this, this))

}