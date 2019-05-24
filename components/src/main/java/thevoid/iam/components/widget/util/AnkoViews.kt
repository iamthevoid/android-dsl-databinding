package thevoid.iam.components.widget.util

import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView
import thevoid.iam.components.widget.constraint.SparseConstraintLayout

fun ViewManager.sparseConstraintLayout(theme: Int = 0, init: SparseConstraintLayout.() -> Unit = {}): SparseConstraintLayout =
    ankoView({ SparseConstraintLayout(it) }, theme) {
        init()
        applySet()
    }