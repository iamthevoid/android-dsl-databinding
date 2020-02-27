package iam.thevoid.noxml.core.mvvm.view

import android.view.View
import androidx.lifecycle.ViewModel

interface MvvmView<VM : ViewModel> {

    val vm: VM

    fun provideViewModel(): iam.thevoid.noxml.core.mvvm.ViewModelBindingProvider

    fun provideContentView(): View?

    fun onConfigureGenericViewModel(viewModel: VM) = Unit

    fun onConfigureViewModel(viewModel: ViewModel) = Unit
}