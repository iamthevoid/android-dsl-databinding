package thevoid.iam.components.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

 class ViewModelBundle(val cls: Class<out ViewModel>, val factory: ((Class<out ViewModel?>) -> ViewModel?)? = null) : ViewModelProvider.Factory {

     override fun <T : ViewModel?> create(modelClass: Class<T>): T =
             (factory?.invoke(modelClass) as? T) ?: throw RuntimeException("Factory not provided")
 }