package iam.thevoid.ankoviews.widget.mvvm

import android.view.View
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import thevoid.iam.components.mvvm.view.MvvmActivity

abstract class AnkoMvvmActivity : MvvmActivity(), AnkoComponent<AnkoMvvmActivity> {

    override fun provideContentView(): View? = createView(AnkoContext.create(this, this))

}