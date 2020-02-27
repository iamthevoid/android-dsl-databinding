package iam.thevoid.noxml.design.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import iam.thevoid.noxml.core.mvvm.view.MvvmView
import iam.thevoid.noxml.core.mvvm.vm.LifecycleTrackingViewModel

abstract class MvvmBottomSheetDialogFragment<VM : ViewModel> : BottomSheetDialogFragment(),
    MvvmView<VM> {

    @Suppress("UNCHECKED_CAST")
    override val vm: VM by lazy {
        viewModels.values.mapNotNull { it as? VM }.firstOrNull()
            ?: throw IllegalArgumentException("View model not provided")
    }

    lateinit var viewModels: Map<Class<out ViewModel>, ViewModel>
        private set

    inline fun <reified VM : ViewModel> viewModel() =
        (viewModels[VM::class.java] as? VM)
            ?: throw RuntimeException("View model with class ${VM::class.qualifiedName} not provided for this dialog fragment")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModels = provideViewModel().bindings.let { bindings ->
            bindings.associate { binding ->
                binding.cls to with(binding) {

                    // Check ViewModel provided to fragment
                    bindingFragment?.let { fragment ->
                        factory?.let { ViewModelProvider(fragment, binding) } ?: ViewModelProvider(fragment) }

                        ?:

                        // Or to activity
                        bindingActivity?.let { activity ->
                            factory?.let { ViewModelProvider(activity, binding) } ?: ViewModelProvider(activity) }

                        ?:

                        // Or no provided at all
                        throw IllegalStateException("No ViewModelBinding provided")

                }[binding.cls].also {viewModel ->

                    (viewModel as? LifecycleTrackingViewModel)?.apply {
                        registerLifecycle(this@MvvmBottomSheetDialogFragment)
                    }

                    onConfigureViewModel(viewModel)

                    (viewModel as? VM)?.let { vm -> onConfigureGenericViewModel(vm) }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        provideContentView()
}