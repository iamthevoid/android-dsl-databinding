/*
 * Copyright (c) 2019. Mike Frolov
 */

package iam.thevoid.noxml.core.mvvm

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlin.reflect.KClass

class ViewModelLazy<VM : ViewModel>(
    private val cls: KClass<VM>,
    private val storageProducer: () -> ViewModelProvider
) : Lazy<VM> {
    private var cached: VM? = null

    override val value: VM
        get() {
            var vm = cached
            if (vm == null) {
                vm = storageProducer().get(cls.java)
                cached = vm
            }
            return vm
        }

    override fun isInitialized() = cached != null
}

@MainThread
inline fun <reified VM : ViewModel> ViewModelStoreOwner.viewModel() =
    ViewModelLazy(VM::class) { ViewModelProvider(this) }

@MainThread
inline fun <reified VM : ViewModel> ViewModelStoreOwner.viewModel(noinline factory: () -> VM) =
    ViewModelLazy(VM::class) {
        ViewModelProvider(
            this,
            SingleProviderFactory(factory)
        )
    }

@MainThread
inline fun <reified VM : ViewModel> Fragment.activityViewModel() =
    ViewModelLazy(VM::class) {
        ViewModelProvider(
            activity!!
        )
    }

@MainThread
inline fun <reified VM : ViewModel> Fragment.activityViewModel(noinline factory: () -> VM) =
    ViewModelLazy(VM::class) {
        ViewModelProvider(
            activity!!,
            SingleProviderFactory(factory)
        )
    }

@MainThread
inline fun <reified VM : ViewModel> Fragment.parentViewModel() =
    ViewModelLazy(VM::class) {
        ViewModelProvider(
            parentFragment!!
        )
    }

@MainThread
inline fun <reified VM : ViewModel> Fragment.parentViewModel(noinline factory: () -> VM) =
    ViewModelLazy(VM::class) {
        ViewModelProvider(
            parentFragment!!,
            SingleProviderFactory(factory)
        )
    }