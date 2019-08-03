package iam.thevoid.common.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import iam.thevoid.common.vm.LifecycleTrackingViewModel


abstract class MvvmActivity<VM : ViewModel> : AppCompatActivity(), MvvmView<VM> {

    lateinit var viewModels: Map<Class<out ViewModel>, ViewModel>
        private set

    override val vm: VM by lazy {
        viewModels.values.mapNotNull { it as? VM }.firstOrNull()
            ?: throw IllegalArgumentException("View model not provided")
    }

    inline fun <reified VM : ViewModel> viewModel() =
        (viewModels[VM::class.java] as? VM)
            ?: throw RuntimeException("View model with class ${VM::class.qualifiedName} not provided for this activity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModels = provideViewModel().bindings.let { bindings ->
            bindings.associate { binding ->
                binding.cls to with(binding) {
                    factory?.let { ViewModelProviders.of(this@MvvmActivity, binding) }
                        ?: ViewModelProviders.of(this@MvvmActivity)
                }[binding.cls].also { viewModel ->

                    (viewModel as? LifecycleTrackingViewModel)?.apply {
                        registerLifecycle(this@MvvmActivity)
                    }

                    onConfigureViewModel(viewModel)

                    (viewModel as? VM)?.let { vm -> onConfigureGenericViewModel(vm) }
                }
            }
        }

        setContentView(provideContentView())
    }
}