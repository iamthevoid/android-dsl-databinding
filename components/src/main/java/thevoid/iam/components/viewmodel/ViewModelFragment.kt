package thevoid.iam.components.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext


abstract class ViewModelFragment : Fragment(), AnkoComponent<ViewModelFragment> {

    lateinit var viewModels: Map<Class<out ViewModel>, ViewModel>
        private set

    inline fun <reified VM : ViewModel> viewModel() =
            (viewModels[VM::class.java] as? VM)
                    ?: throw RuntimeException("View model with class ${VM::class.qualifiedName} not provided for this fragment")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModels = provideVMBundle().bundles.let { bundles ->
            bundles.associate { bundle ->
                bundle.cls to with(bundle) {
                    factory?.let { ViewModelProviders.of(this@ViewModelFragment, bundle) }
                            ?: ViewModelProviders.of(this@ViewModelFragment)
                }[bundle.cls].also {
                    (it as? LifecycleTrackingViewModel)?.registerLifecycle(this)
                    prepare(it)
                }
            }
        }
    }

    open fun prepare(viewModel: ViewModel) = Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            context?.let { createView(AnkoContext.create(it, this, false)) }

    abstract fun provideVMBundle(): ViewModelBundleProvider
}