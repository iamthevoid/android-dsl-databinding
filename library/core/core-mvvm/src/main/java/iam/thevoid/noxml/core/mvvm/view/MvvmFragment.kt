@file:Suppress("unused")

package iam.thevoid.noxml.core.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import iam.thevoid.noxml.core.mvvm.vm.LifecycleTrackingViewModel

@Deprecated("Class will be removed without replacement. Use fun Fragment.attachViewModel to attach view model to fragment lifecycle")
abstract class MvvmFragment<VM : ViewModel> : Fragment(), MvvmView<VM> {

    @Suppress("UNCHECKED_CAST")
    override val vm: VM by lazy {
        viewModels.values.mapNotNull { it as? VM }.firstOrNull()
            ?: throw IllegalArgumentException("View model not provided")
    }

    lateinit var viewModels: Map<Class<out ViewModel>, ViewModel>
        private set

    inline fun <reified VM : ViewModel> viewModel() =
        (viewModels[VM::class.java] as? VM)
            ?: throw RuntimeException("View model with class ${VM::class.qualifiedName} not provided for this fragment")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModels = provideViewModel().bindings.let { bindings ->
            bindings.associate { binding ->
                binding.cls to with(binding) {

                    // Check ViewModel provided to fragment
                    bindingFragment?.let { fragment ->
                        factory?.let { ViewModelProvider(fragment, binding) } ?: ViewModelProvider(fragment)
                    }

                        ?:

                        // Or to activity
                        bindingActivity?.let { activity ->
                            factory?.let { ViewModelProvider(activity, binding) } ?: ViewModelProvider(activity)
                        }

                        ?:

                        // Or no provided at all
                        throw IllegalStateException("No ViewModelBinding provided")

                }[binding.cls].also { viewModel ->
                    (viewModel as? LifecycleTrackingViewModel)?.apply {
                        registerLifecycle(this@MvvmFragment)
                    }

                    onConfigureViewModel(viewModel)

                    @Suppress("UNCHECKED_CAST")
                    (viewModel as? VM)?.let { vm -> onConfigureGenericViewModel(vm) }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        provideContentView()
}