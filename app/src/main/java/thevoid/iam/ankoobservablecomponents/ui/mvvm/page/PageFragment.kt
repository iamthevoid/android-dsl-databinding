package thevoid.iam.ankoobservablecomponents.ui.mvvm.page

import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.PARENT_ID
import iam.thevoid.ae.resetFocus
import iam.thevoid.ankoviews.widget.mvvm.AnkoMvvmFragment
import iam.thevoid.ankoviews.widget.sparseConstraintLayout
import iam.thevoid.e.safe
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.matchConstraint
import thevoid.iam.ankoobservablecomponents.R
import thevoid.iam.components.mvvm.ViewModelBindingProvider
import thevoid.iam.components.mvvm.createBinding
import thevoid.iam.components.widget.ext.onFocusChange
import thevoid.iam.components.widget.ext.onTextChanges
import thevoid.iam.components.widget.ext.setRequestInput
import thevoid.iam.components.widget.ext.setText

class PageFragment : AnkoMvvmFragment<PageViewModel>() {

    var et: EditText? = null

    override fun createView(ui: AnkoContext<AnkoMvvmFragment<PageViewModel>>): View =
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
                setText(vm.changes.map { it.elem?.s?.toString().safe })
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


    override fun provideViewModel(): ViewModelBindingProvider = createBinding(PageViewModel::class.java)

}