package thevoid.iam.components.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import thevoid.iam.components.mvvm.viewmodel.LifecycleTrackingViewModel


abstract class MvvmFragment<VM : ViewModel> : Fragment(), MvvmView<VM> {

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
                        factory?.let { ViewModelProviders.of(fragment, binding) } ?: ViewModelProviders.of(fragment)
                    }

                        ?:

                        // Or to activity
                        bindingActivity?.let { activity ->
                            factory?.let { ViewModelProviders.of(activity, binding) } ?: ViewModelProviders.of(activity)
                        }

                        ?:

                        // Or no provided at all
                        throw IllegalStateException("No ViewModelBinding provided")

                }[binding.cls].also { viewModel ->
                    (viewModel as? LifecycleTrackingViewModel)?.apply {
                        registerLifecycle(this@MvvmFragment)
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