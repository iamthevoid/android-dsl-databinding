package iam.thevoid.noxml.design.change

import com.google.android.material.appbar.AppBarLayout
import iam.thevoid.e.safe
import kotlin.math.absoluteValue

data class OnOffsetChange(val appBarLayout: AppBarLayout?, val verticalOffset: Int) {
    fun toPercent() =
        verticalOffset.toFloat().absoluteValue / appBarLayout?.totalScrollRange.safe().toFloat()
}