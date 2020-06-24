package iam.thevoid.noxml.recycler.util

import android.view.View
import android.view.ViewGroup
import iam.thevoid.e.safe
import iam.thevoid.noxml.Config

@Synchronized
internal fun isRunningTest() = Config.ESPRESSO_TEST_RUNNING

internal fun isCallingFromMatcher() =
    Thread.currentThread().stackTrace.any { ste ->
        ste.methodName.contains("match", true) &&
                ste.className.contains("RecyclerViewActions", true)
    }.safe()


internal fun View.onItAndChildrenTree(action: View.() -> Unit) {
    if (this is ViewGroup) {
        for (i in 0 until childCount)
            getChildAt(i).onItAndChildrenTree(action)
    } else {
        action()
    }
}