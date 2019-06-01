package thevoid.iam.components.mvvm.view

import android.view.View
import thevoid.iam.components.mvvm.ViewModelBundleProvider

interface MvvmView {

    fun provideViewModel(): ViewModelBundleProvider

    fun provideContentView(): View?

}