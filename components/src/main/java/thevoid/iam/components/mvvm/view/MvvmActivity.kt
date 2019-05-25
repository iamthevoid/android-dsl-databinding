package thevoid.iam.components.mvvm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import thevoid.iam.components.mvvm.viewmodel.LifecycleTrackingViewModel
import thevoid.iam.components.mvvm.ViewModelBundleProvider


abstract class MvvmActivity : AppCompatActivity(), AnkoComponent<MvvmActivity> {

    lateinit var viewModels: Map<Class<out ViewModel>, ViewModel>
        private set

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

        setContentView(createView(AnkoContext.create(this, this)))
    }

    open fun onConfigureViewModel(viewModel: ViewModel) = Unit

    abstract fun provideViewModel(): ViewModelBundleProvider
}