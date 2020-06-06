@file:Suppress("unused")

package iam.thevoid.noxml.core.mvvm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import iam.thevoid.noxml.core.mvvm.vm.LifecycleTrackingViewModel


abstract class MvvmActivity<VM : ViewModel> : AppCompatActivity(), MvvmView<VM> {

    lateinit var viewModels: Map<Class<out ViewModel>, ViewModel>
        private set

    @Suppress("UNCHECKED_CAST")
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
                    factory?.let { ViewModelProvider(this@MvvmActivity, binding) }
                        ?: ViewModelProvider(this@MvvmActivity)
                }[binding.cls].also { viewModel ->

                    (viewModel as? LifecycleTrackingViewModel)?.apply {
                        registerLifecycle(this@MvvmActivity)
                    }

                    onConfigureViewModel(viewModel)

                    @Suppress("UNCHECKED_CAST")
                    (viewModel as? VM)?.let { vm -> onConfigureGenericViewModel(vm) }
                }
            }
        }

        setContentView(provideContentView())
    }
}