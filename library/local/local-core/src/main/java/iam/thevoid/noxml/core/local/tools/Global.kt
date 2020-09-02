package iam.thevoid.noxml.core.local.tools

import android.view.View
import androidx.annotation.IdRes

inline fun <reified T> View.lazyDelegate(@IdRes id: Int, factory: () -> T, apply: (T) -> Unit = {}): T {
    return (getTag(id) as? T) ?: factory().also(apply).also { setTag(id, it) }
}

fun View.isLazyDelegateAttached(@IdRes id: Int): Boolean = getTag(id) != null