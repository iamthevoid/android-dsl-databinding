package thevoid.iam.components.widget.util

import android.annotation.TargetApi
import android.os.Build
import android.view.View
import iam.thevoid.ae.actionBarItemBackgroundResource
import iam.thevoid.ae.drawable
import iam.thevoid.ae.selectableItemBackgroundResource
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.dimenAttr

/**
 * ATTR
 */

val View.actionBarHeight
    get() = dimenAttr(android.R.attr.actionBarSize)


/**
 * CLICKABLE
 */

// FULL SQUARE

@TargetApi(value = Build.VERSION_CODES.M)
fun View.setRippleClickForeground() {
    foreground = context.drawable(context.selectableItemBackgroundResource)
    setClickable()
}

fun View.setRippleClickBackground() {
    backgroundResource = context.selectableItemBackgroundResource
    setClickable()
}

fun View.setRippleClickAnimation() =
    if (canUseForeground) setRippleClickForeground() else setRippleClickBackground()

// ROUNDED

/**
 * Looks bad
 */
//@TargetApi(value = Build.VERSION_CODES.M)
//fun View.setRoundRippleClickForeground() {
//    foreground = context.drawable(context.actionBarItemBackgroundResource)
//    setClickable()
//}

fun View.setRoundRippleClickBackground() {
    backgroundResource = context.actionBarItemBackgroundResource
    setClickable()
}

fun View.setRoundRippleClickAnimation() = setRoundRippleClickBackground()

// HELPER

private val canUseForeground
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

fun View.setClickable() {
    isClickable = true
    isFocusable = true
}

/**
 * MEASURE SPEC
 */

val unspecified
    get() = View.MeasureSpec.UNSPECIFIED

val atMost
    get() = View.MeasureSpec.AT_MOST

val exactly
    get() = View.MeasureSpec.EXACTLY
