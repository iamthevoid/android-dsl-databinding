package iam.thevoid.noxml.core.mvvm.view

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import iam.thevoid.noxml.core.mvvm.vm.LifecycleTrackingViewModel

fun Fragment.attachViewModel(viewModel: LifecycleTrackingViewModel) {
    viewModel.registerLifecycle(this)
}

fun ComponentActivity.attachViewModel(viewModel: LifecycleTrackingViewModel) {
    viewModel.registerLifecycle(this)
}

fun androidx.core.app.ComponentActivity.attachViewModel(viewModel: LifecycleTrackingViewModel) {
    viewModel.registerLifecycle(this)
}