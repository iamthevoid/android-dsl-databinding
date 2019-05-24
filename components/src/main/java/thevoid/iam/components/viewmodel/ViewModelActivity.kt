package thevoid.iam.components.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext


abstract class ViewModelActivity : AppCompatActivity(), AnkoComponent<ViewModelActivity> {

    lateinit var viewModels: Map<Class<out ViewModel>, ViewModel>
        private set

    inline fun <reified VM : ViewModel> viewModel() =
        (viewModels[VM::class.java] as? VM)
            ?: throw RuntimeException("View model with class ${VM::class.qualifiedName} not provided for this activity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModels = provideVMBundle().bundles.let { bundles ->
            bundles.associate { bundle ->
                bundle.cls to with(bundle) {
                    factory?.let { ViewModelProviders.of(this@ViewModelActivity, bundle) }
                        ?: ViewModelProviders.of(this@ViewModelActivity)
                }[bundle.cls].also {
                    (it as? LifecycleTrackingViewModel)?.registerLifecycle(this)
                    prepare(it)
                }
            }
        }

        setContentView(createView(AnkoContext.create(this, this)))
    }

    open fun prepare(viewModel: ViewModel) = Unit

    abstract fun provideVMBundle(): ViewModelBundleProvider
}