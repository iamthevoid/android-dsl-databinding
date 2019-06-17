package thevoid.iam.components.mvvm.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import thevoid.iam.components.mvvm.viewmodel.LifecycleTrackingViewModel
import thevoid.iam.components.mvvm.ViewModelBundleProvider


abstract class MvvmActivity<VM : ViewModel> : AppCompatActivity(), MvvmView {

    lateinit var viewModels: Map<Class<out ViewModel>, ViewModel>
        private set

    val vm: VM by lazy {
        viewModels.values.mapNotNull { it as? VM }.firstOrNull()
            ?: throw IllegalArgumentException("View model not provided")
    }

    inline fun <reified VM : ViewModel> viewModel() =
        (viewModels[VM::class.java] as? VM)
            ?: throw RuntimeException("View model with class ${VM::class.qualifiedName} not provided for this activity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModels = provideViewModel().bundles.let { bundles ->
            bundles.associate { bundle ->
                bundle.cls to with(bundle) {
                    factory?.let { ViewModelProviders.of(this@MvvmActivity, bundle) }
                        ?: ViewModelProviders.of(this@MvvmActivity)
                }[bundle.cls].also {
                    (it as? LifecycleTrackingViewModel)?.registerLifecycle(this)
                    onConfigureViewModel(it)
                }
            }
        }

        setContentView(provideContentView())
    }

    open fun onConfigureViewModel(viewModel: ViewModel) = Unit
}