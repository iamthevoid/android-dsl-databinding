package iam.thevoid.noxml.core.local.tools

import android.view.View
import androidx.annotation.IdRes

inline fun <V : View, reified T> V.lazyDelegate(
    @IdRes id: Int,
    factory: () -> T,
    apply: (T) -> Unit = {}
): T = (getTag(id) as? T) ?: factory().also(apply).also { setTag(id, it) }