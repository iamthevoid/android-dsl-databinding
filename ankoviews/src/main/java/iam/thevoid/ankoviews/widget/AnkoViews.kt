package iam.thevoid.ankoviews.widget

import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView
import iam.thevoid.ankoviews.widget.constraint.SparseConstraintLayout


/**
 * ViewManager.sparseConstraintLayout allows users create instance of ConstraintLayout which let declare constraints
 * for each view separately, not in one fun 'applyConstraintSet'. Example
 *
 * sparseConstraintLayout {
 *      editText {
 *          id = R.id.et
 *          hint = "Enter something"
 *          onTextChanges(viewModel<PageViewModel>().changes)
 *      }.lparams(matchConstraint, wrapContent)
 *          .constraint {
 *              connect(
 *                  TOP to TOP of PARENT_ID,
 *                  START to START of PARENT_ID,
 *                  END to END of PARENT_ID
 *              )
 *      }
 *
 *      textView {
 *          id = R.id.text1
 *          text = "Entered"
 *      }.constraint {
 *          connect(
 *              TOP to BOTTOM of R.id.et,
 *              START to START of PARENT_ID,
 *              END to START of R.id.text2,
 *              BOTTOM to BOTTOM of PARENT_ID
 *          )
 *
 *          horizontalChainStyle = ConstraintSet.CHAIN_SPREAD
 *          horizontalWeight = 1f
 *      }
 *
 *      textView {
 *          id = R.id.text2
 *          textSize = dip(16).toFloat()
 *          setText(viewModel<PageViewModel>().changes.observe { it.elem?.s?.toString().safe })
 *      }.constraint {
 *          connect(
 *              TOP to BOTTOM of R.id.et,
 *              START to END of R.id.text1,
 *              END to END of PARENT_ID,
 *              BOTTOM to BOTTOM of PARENT_ID
 *          )
 *
 *          horizontalChainStyle = ConstraintSet.CHAIN_SPREAD
 *          horizontalWeight = 1f
 *      }
 * }
 */
fun ViewManager.sparseConstraintLayout(theme: Int = 0, init: SparseConstraintLayout.() -> Unit = {}): SparseConstraintLayout =
    ankoView({ SparseConstraintLayout(it) }, theme) {
        init()
        applySet()
    }