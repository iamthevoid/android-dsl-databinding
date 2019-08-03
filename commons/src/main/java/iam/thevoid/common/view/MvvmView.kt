package iam.thevoid.common.view

import android.view.View
import androidx.lifecycle.ViewModel
import iam.thevoid.common.ViewModelBindingProvider

interface MvvmView<VM : ViewModel> {

    val vm: VM

    fun provideViewModel(): ViewModelBindingProvider

    fun provideContentView(): View?

    fun onConfigureGenericViewModel(viewModel: VM) = Unit

    fun onConfigureViewModel(viewModel: ViewModel) = Unit
}