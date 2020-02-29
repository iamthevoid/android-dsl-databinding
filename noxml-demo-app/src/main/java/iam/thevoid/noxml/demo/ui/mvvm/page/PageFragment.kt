package iam.thevoid.noxml.demo.ui.mvvm.page

import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.PARENT_ID
import iam.thevoid.ae.resetFocus
import iam.thevoid.noxml.core.mvvm.viewModel
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import iam.thevoid.noxml.demo.ui.BaseFragment
import iam.thevoid.noxml.anko.sparseConstraintLayout
import iam.thevoid.noxml.demo.R
import iam.thevoid.noxml.rx.extensions.onFocusChange
import iam.thevoid.noxml.rx.extensions.onTextChanges
import iam.thevoid.noxml.rx.extensions.setRequestInput
import iam.thevoid.noxml.rx.extensions.setText
import org.jetbrains.anko.constraint.layout.matchConstraint

class PageFragment : BaseFragment() {

    val vm by viewModel<PageViewModel>()

    var et: EditText? = null

    override fun createView(ui: AnkoContext<BaseFragment>): View =
        ui.sparseConstraintLayout {

            et = editText {
                id = R.id.et
                hint = "Enter something"
                onTextChanges(vm.changes)
                onFocusChange(vm.input)
                setRequestInput(vm.input)
            }.lparams(matchConstraint, wrapContent)
                .constraint {
                    connect(
                        TOP to TOP of PARENT_ID,
                        START to START of PARENT_ID,
                        END to END of PARENT_ID
                    )
                }

            setOnClickListener { et?.resetFocus() }
            textView {
                id = R.id.text1
                text = "Entered"
            }.constraint {
                connect(
                    TOP to BOTTOM of R.id.et,
                    START to START of PARENT_ID,
                    END to START of R.id.text2,
                    BOTTOM to BOTTOM of PARENT_ID
                )

                horizontalChainStyle = ConstraintSet.CHAIN_SPREAD
                horizontalWeight = 1f
            }

            textView {
                id = R.id.text2
                textSize = dip(16).toFloat()
                setText(vm.changes.onlyPresent().map { "${it.s}" })
            }.constraint {
                connect(
                    TOP to BOTTOM of R.id.et,
                    START to END of R.id.text1,
                    END to END of PARENT_ID,
                    BOTTOM to BOTTOM of PARENT_ID
                )


                horizontalChainStyle = ConstraintSet.CHAIN_SPREAD
                horizontalWeight = 1f
            }
        }

}