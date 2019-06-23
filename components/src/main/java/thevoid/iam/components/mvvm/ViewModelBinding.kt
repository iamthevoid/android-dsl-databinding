package thevoid.iam.components.mvvm

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelBinding(
    val cls: Class<out ViewModel>,
    val bindingActivity: FragmentActivity? = null,
    val bindingFragment: Fragment? = null,
    val factory: ((Class<out ViewModel?>) -> ViewModel?)? = null
) : ViewModelProvider.Factory {

    init {
        if (bindingFragment == null && bindingActivity == null)
            throw IllegalStateException("There is must be provided activity or fragment (as lifecycle owner to bind with)")

        if (bindingFragment != null && bindingActivity != null)
            throw IllegalStateException("There is must be provided activity or fragment (as lifecycle owner to bind with), but not both")
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        (factory?.invoke(modelClass) as? T) ?: throw RuntimeException("Factory not provided")
}