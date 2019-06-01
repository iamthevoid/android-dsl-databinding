package iam.thevoid.ankoviews.widget

import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView
import iam.thevoid.ankoviews.widget.constraint.SparseConstraintLayout

fun ViewManager.sparseConstraintLayout(theme: Int = 0, init: SparseConstraintLayout.() -> Unit = {}): SparseConstraintLayout =
    ankoView({ SparseConstraintLayout(it) }, theme) {
        init()
        applySet()
    }