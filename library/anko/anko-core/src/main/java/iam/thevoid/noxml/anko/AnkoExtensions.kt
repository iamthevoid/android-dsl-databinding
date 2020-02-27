package iam.thevoid.noxml.anko

import android.view.View
import org.jetbrains.anko.dimenAttr

/**
 * ATTR
 */

/**
 * Attribute dimen of action bar
 */

val View.actionBarHeight : Int
    get() = dimenAttr(android.R.attr.actionBarSize)
