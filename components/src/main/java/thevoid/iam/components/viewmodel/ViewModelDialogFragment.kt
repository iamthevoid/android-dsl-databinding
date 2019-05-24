package thevoid.iam.components.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext


abstract class ViewModelDialogFragment : DialogFragment(), AnkoComponent<ViewModelDialogFragment> {

    lateinit var viewModels: Map<Class<out ViewModel>, ViewModel>
        private set

    inline fun <reified VM : ViewModel> viewModel() =
            (viewModels[VM::class.java] as? VM)
                    ?: throw RuntimeException("View model with class ${VM::class.qualifiedName} not provided for this dialog fragment")

    open val style
        get() = android.R.style.Theme_Black_NoTitleBar_Fullscreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (style != -1)
            setStyle(STYLE_NORMAL, style)

        viewModels = provideVMBundle().bundles.let { bundles ->
            bundles.associate { bundle ->
                bundle.cls to with(bundle) {
                    factory?.let { ViewModelProviders.of(this@ViewModelDialogFragment, bundle) }
                            ?: ViewModelProviders.of(this@ViewModelDialogFragment)
                }[bundle.cls].also {
                    (it as? LifecycleTrackingViewModel)?.apply {
                        registerLifecycle(this@ViewModelDialogFragment)
                    }
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