package thevoid.iam.components.mvvm

import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

/**
 * Helper method:
 *
 * Can get any of arguments -> ViewModel class, ViewModel (factory), Pair of ViewModel class and
 * ViewModel factory, ViewModelBundle
 *
 * for example:
 * createBundle(VM::class to { VM(6) }, VM2::class.java)
 *
 * Any other passed values will be ignored. If passed two or more same ViewModels (same class)
 * only first will be used, other will be ignored.
 */
@Suppress("UNCHECKED_CAST")
fun createBundle(bundle: Any, vararg bundles: Any): ViewModelBundleProvider =
    mutableListOf<ViewModelBundle>().let { filtered ->
        mutableListOf(bundle).apply { addAll(bundles) }.mapNotNull { raw ->
            when (raw) {
                is ViewModelBundle -> raw
                is ViewModel -> ViewModelBundle(raw.javaClass) { raw }
                is Class<*> -> (raw as? Class<out ViewModel>)?.let { ViewModelBundle(it) }
                is KClass<*> -> (raw as? KClass<out ViewModel>)?.let { ViewModelBundle(it.java) }
                is Pair<*, *> ->
                    (raw.first as? Class<out ViewModel>)?.let { cls ->
                        (raw.second as? () -> ViewModel)?.let { factory ->
                            ViewModelBundle(cls) { factory() }
                        } ?: ViewModelBundle(cls)
                    } ?: (raw.first as? KClass<out ViewModel>)?.let { kcls ->
                        (raw.second as? () -> ViewModel)?.let { factory ->
                            ViewModelBundle(kcls.java) { factory() }
                        } ?: ViewModelBundle(kcls.java)
                    }
                else -> null
            }
        }.filterTo(filtered) { filtered.none { alreadyFiltered -> alreadyFiltered.cls == it.cls } }
            .let { filtered }
            .let { ListBundleProvider(it) }
    }