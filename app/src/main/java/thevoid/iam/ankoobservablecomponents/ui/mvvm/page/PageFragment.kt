package thevoid.iam.ankoobservablecomponents.ui.mvvm.page

import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.PARENT_ID
import iam.thevoid.ankoviews.widget.mvvm.AnkoMvvmFragment
import iam.thevoid.ankoviews.widget.sparseConstraintLayout
import iam.thevoid.e.safe
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.matchConstraint
import thevoid.iam.ankoobservablecomponents.R
import thevoid.iam.components.mvvm.ViewModelBundleProvider
import thevoid.iam.components.mvvm.createBundle
import thevoid.iam.components.widget.ext.onTextChanges
import thevoid.iam.components.widget.ext.setText

class PageFragment : AnkoMvvmFragment<PageViewModel>() {

    override fun createView(ui: AnkoContext<AnkoMvvmFragment<PageViewModel>>): View =
        ui.sparseConstraintLayout {

            editText {
                id = R.id.et
                hint = "Enter something"
                onTextChanges(vm.changes)
            }.lparams(matchConstraint, wrapContent)
                .constraint {
                    connect(
                        TOP to TOP of PARENT_ID,
                        START to START of PARENT_ID,
                        END to END of PARENT_ID
                    )
                }

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
                setText(vm.changes.observe { it.elem?.s?.toString().safe })
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


    override fun provideViewModel(): ViewModelBundleProvider = createBundle(PageViewModel::class.java)

}