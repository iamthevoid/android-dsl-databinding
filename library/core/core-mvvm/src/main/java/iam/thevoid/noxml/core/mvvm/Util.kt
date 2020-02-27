package iam.thevoid.noxml.core.mvvm

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

/**
 * Util helps to provide a binding for activity or fragment
 *
 * At first: 'create binding' is extension method and bind ViewModel to lifecycle owner that extended, activity or fragment.
 *
 * For example if one Activity holds few Fragments and Fragments must share ViewModel with this Activity than better
 * solution to bind ViewModel with fragment is call activity.createBinding in fragment. If fragment use own ViewModel
 * than it creates binding with own createBinding method.
 *
 * Any of this binding markers (initialize objects) possible:
 * 1) ViewModel class
 *  ex: MainViewModel::class.java
 *
 * 2) ViewModel kclass
 *  ex: MainViewModel::class
 *
 * 3) ViewModelBinding
 *  ex: ViewModelBinding(MainViewModel::class.java, activity = this) { MainViewModel(2) }
 *
 * 4) ViewModel (than initialization will be used as factory result)
 *  ex: MainViewModel(2)
 *
 * 5) Pair of ViewModel class and ViewModel factory
 *  ex: MainViewModel::class.java to MainViewModel(2)
 *
 * ALL ANOTHER MARKERS WILL BE IGNORED
 */

@Suppress("UNCHECKED_CAST")
inline fun <reified T : FragmentActivity> T?.createBinding(binding: Any, vararg bindingMarkers: Any): iam.thevoid.noxml.core.mvvm.ViewModelBindingProvider =
    if (this == null)
        throw IllegalStateException("Could not bind ViewModel with ${T::class.java.simpleName}, because ${T::class.java.simpleName} is null")
    else
        mutableListOf<iam.thevoid.noxml.core.mvvm.ViewModelBinding>().let { filtered ->
            mutableListOf(binding).apply { addAll(bindingMarkers) }.mapNotNull { raw ->
                when (raw) {
                    is iam.thevoid.noxml.core.mvvm.ViewModelBinding -> raw
                    is ViewModel -> iam.thevoid.noxml.core.mvvm.ViewModelBinding(
                        raw.javaClass,
                        bindingActivity = this
                    ) { raw }
                    is Class<*> -> (raw as? Class<out ViewModel>)?.let {
                        iam.thevoid.noxml.core.mvvm.ViewModelBinding(
                            it,
                            bindingActivity = this
                        )
                    }
                    is KClass<*> -> (raw as? KClass<out ViewModel>)?.let {
                        iam.thevoid.noxml.core.mvvm.ViewModelBinding(
                            it.java,
                            bindingActivity = this
                        )
                    }
                    is Pair<*, *> ->
                        (raw.first as? Class<out ViewModel>)?.let { cls ->
                            (raw.second as? () -> ViewModel)?.let { factory ->
                                iam.thevoid.noxml.core.mvvm.ViewModelBinding(
                                    cls,
                                    bindingActivity = this
                                ) { factory() }
                            } ?: iam.thevoid.noxml.core.mvvm.ViewModelBinding(
                                cls,
                                bindingActivity = this
                            )
                        } ?: (raw.first as? KClass<out ViewModel>)?.let { kcls ->
                            (raw.second as? () -> ViewModel)?.let { factory ->
                                iam.thevoid.noxml.core.mvvm.ViewModelBinding(
                                    kcls.java,
                                    bindingActivity = this
                                ) { factory() }
                            } ?: iam.thevoid.noxml.core.mvvm.ViewModelBinding(
                                kcls.java,
                                bindingActivity = this
                            )
                        }
                    else -> null
                }
            }.filterTo(filtered) { filtered.none { alreadyFiltered -> alreadyFiltered.cls == it.cls } }
                .let { filtered }
                .let { iam.thevoid.noxml.core.mvvm.ListBindingProvider(it) }
        }

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Fragment> T?.createBinding(bindingMarker: Any, vararg bindingMarkers: Any): iam.thevoid.noxml.core.mvvm.ViewModelBindingProvider =
    if (this == null)
        throw IllegalStateException("Could not bind ViewModel with ${T::class.java.simpleName}, because ${T::class.java.simpleName} is null")
    else
        mutableListOf<iam.thevoid.noxml.core.mvvm.ViewModelBinding>().let { filtered ->
            mutableListOf(bindingMarker).apply { addAll(bindingMarkers) }.mapNotNull { raw ->
                when (raw) {
                    is iam.thevoid.noxml.core.mvvm.ViewModelBinding -> raw
                    is ViewModel -> iam.thevoid.noxml.core.mvvm.ViewModelBinding(
                        raw.javaClass,
                        bindingFragment = this
                    ) { raw }
                    is Class<*> -> (raw as? Class<out ViewModel>)?.let {
                        iam.thevoid.noxml.core.mvvm.ViewModelBinding(
                            it,
                            bindingFragment = this
                        )
                    }
                    is KClass<*> -> (raw as? KClass<out ViewModel>)?.let {
                        iam.thevoid.noxml.core.mvvm.ViewModelBinding(
                            it.java,
                            bindingFragment = this
                        )
                    }
                    is Pair<*, *> ->
                        (raw.first as? Class<out ViewModel>)?.let { cls ->
                            (raw.second as? () -> ViewModel)?.let { factory ->
                                iam.thevoid.noxml.core.mvvm.ViewModelBinding(
                                    cls,
                                    bindingFragment = this
                                ) { factory() }
                            } ?: iam.thevoid.noxml.core.mvvm.ViewModelBinding(
                                cls,
                                bindingFragment = this
                            )
                        } ?: (raw.first as? KClass<out ViewModel>)?.let { kcls ->
                            (raw.second as? () -> ViewModel)?.let { factory ->
                                iam.thevoid.noxml.core.mvvm.ViewModelBinding(
                                    kcls.java,
                                    bindingFragment = this
                                ) { factory() }
                            } ?: iam.thevoid.noxml.core.mvvm.ViewModelBinding(
                                kcls.java,
                                bindingFragment = this
                            )
                        }
                    else -> null
                }
            }.filterTo(filtered) { filtered.none { alreadyFiltered -> alreadyFiltered.cls == it.cls } }
                .let { filtered }
                .let { iam.thevoid.noxml.core.mvvm.ListBindingProvider(it) }
        }

/**
 * Also you can merge results of createBinding or another ways of create binding providers
 *  ex:
 *
 *  WeatherFragment : MvvmFragment() {
 *
 *      override fun provideViewModel() = merge(
 *          createBinding(WeatherViewModel::class),
 *          activity.createBinding(MainViewModel::class)
 *      )
 *
 *      .......................................
 *  }
 */

fun merge(provider: iam.thevoid.noxml.core.mvvm.ViewModelBindingProvider, vararg providers: iam.thevoid.noxml.core.mvvm.ViewModelBindingProvider) : iam.thevoid.noxml.core.mvvm.ViewModelBindingProvider =
    iam.thevoid.noxml.core.mvvm.ListBindingProvider(mutableListOf(provider.bindings).apply {
        addAll(
            providers.map { it.bindings })
    }.flatten())