package thevoid.iam.components.widget.constraint

import android.content.Context
import android.view.View
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder
import org.jetbrains.anko.constraint.layout.ViewConstraintBuilder
import org.jetbrains.anko.constraint.layout._ConstraintLayout

open class SparseConstraintLayout(ctx: Context) : _ConstraintLayout(ctx) {

    private val constraintSet by lazy { ConstraintSetBuilder().also { it.clone(this) } }

    private val actions = mutableListOf<ConstraintSetBuilder.() -> Unit>()
    private val connections = mutableListOf<ConstraintSetBuilder.() -> Unit>()

    fun <V : View> V.constraint(builder: ViewConstraintBuilder.() -> Unit): V {
        actions.add {
            apply {
                this@constraint {
                    builder()
                }
            }
        }
        return this
    }

    fun connect(vararg connection: ConstraintSetBuilder.Connection) {
        connections.add { connect(*connection) }
    }

    internal fun applySet() {
        actions.forEach {
            constraintSet.it()
        }

        connections.forEach {
            constraintSet.it()
        }

        constraintSet.applyTo(this)
    }

    infix fun ConstraintSetBuilder.Connection.BasicConnection.margin(margin: Int) = ConstraintSetBuilder.Connection.MarginConnection(from, to, margin)
}