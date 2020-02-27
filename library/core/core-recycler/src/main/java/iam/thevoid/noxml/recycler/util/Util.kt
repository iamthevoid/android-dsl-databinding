package iam.thevoid.noxml.recycler.util

import android.view.View
import android.view.ViewGroup
import iam.thevoid.e.safe
import java.util.concurrent.atomic.AtomicBoolean

private val isRunningTest by lazy {
    AtomicBoolean(
        classExists("android.support.test.espresso.Espresso").takeIf { it }
            ?: classExists("androidx.test.espresso.Espresso")
    )
}

@Synchronized
internal fun isRunningTest() = isRunningTest.get()

internal fun isCallingFromMatcher() =
    Thread.currentThread().stackTrace.getOrNull(7)?.let { ste ->
        ste.methodName.contains("match", true) ||
                ste.className.contains("RecyclerViewActions", true)
    }.safe()

private fun classExists(className: String) =
    try {
        Class.forName(className)
        true
    } catch (e: ClassNotFoundException) {
        false
    }

internal fun View.onItAndChildrenTree(action: View.() -> Unit) {
    if (this is ViewGroup) {
        for (i in 0 until childCount)
            getChildAt(i).onItAndChildrenTree(action)
    } else {
        action()
    }
}