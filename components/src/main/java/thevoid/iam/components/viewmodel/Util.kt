package thevoid.iam.components.viewmodel

import androidx.lifecycle.ViewModel

/**
 * Helper method:
 *
 * Can get any of arguments -> ViewModel class, ViewModel (factory), Pair of ViewModel class and
 * ViewModel factory, ViewModelBundle
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
                    is Pair<*, *> -> (raw.first as? Class<out ViewModel>)?.let { cls ->
                        (raw.second as? (Class<out ViewModel>) -> ViewModel)?.let { factory ->
                            ViewModelBundle(cls, factory)
                        } ?: ViewModelBundle(cls)
                    }
                    else -> null
                }
            }.filterTo(filtered) { filtered.none { alreadyFiltered -> alreadyFiltered.cls == it.cls } }
                    .let { filtered }
                    .let { ListBundleProvider(it) }
        }