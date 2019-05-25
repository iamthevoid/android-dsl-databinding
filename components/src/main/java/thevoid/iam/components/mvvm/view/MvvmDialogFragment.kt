package thevoid.iam.components.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import thevoid.iam.components.mvvm.viewmodel.LifecycleTrackingViewModel
import thevoid.iam.components.mvvm.ViewModelBundleProvider

abstract class MvvmDialogFragment : DialogFragment(), AnkoComponent<MvvmDialogFragment> {

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

        viewModels = provideViewModel().bundles.let { bundles ->
            bundles.associate { bundle ->
                bundle.cls to with(bundle) {

                    activity?.let { activity ->
                        factory?.let { ViewModelProviders.of(activity, bundle) } ?: ViewModelProviders.of(activity)
                    } ?: this@MvvmDialogFragment.let { fragment ->
                        factory?.let { ViewModelProviders.of(fragment, bundle) } ?: ViewModelProviders.of(fragment)
                    }

                }[bundle.cls].also {
                    (it as? LifecycleTrackingViewModel)?.apply {
                        registerLifecycle(this@MvvmDialogFragment)
                    }
                    onConfigureViewModel(it)
                }
            }
        }
    }

    open fun onConfigureViewModel(viewModel: ViewModel) = Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        context?.let { createView(AnkoContext.create(it, this, false)) }

    abstract fun provideViewModel(): ViewModelBundleProvider
}