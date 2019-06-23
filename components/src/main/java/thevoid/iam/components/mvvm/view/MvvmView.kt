package thevoid.iam.components.mvvm.view

import android.view.View
import androidx.lifecycle.ViewModel
import thevoid.iam.components.mvvm.ViewModelBindingProvider

interface MvvmView<VM : ViewModel> {

    val vm: VM

    fun provideViewModel(): ViewModelBindingProvider

    fun provideContentView(): View?

    fun onConfigureGenericViewModel(viewModel: VM) = Unit

    fun onConfigureViewModel(viewModel: ViewModel) = Unit
}