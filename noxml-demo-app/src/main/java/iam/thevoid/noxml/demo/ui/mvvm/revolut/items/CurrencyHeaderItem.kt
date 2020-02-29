package iam.thevoid.noxml.demo.ui.mvvm.revolut.items

import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import iam.thevoid.noxml.anko.coroutines.AnkoCoroutinesLayout
import iam.thevoid.noxml.coroutines.extensions.afterTextChanges
import iam.thevoid.noxml.coroutines.extensions.setText
import iam.thevoid.noxml.demo.R
import iam.thevoid.noxml.demo.ui.mvvm.revolut.RevolutViewModel
import iam.thevoid.noxml.demo.util.codeToValue
import iam.thevoid.noxml.demo.util.rateInputFilter
import iam.thevoid.noxml.demo.util.setImageUrl
import iam.thevoid.noxml.demo.util.toImageUrl
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.jetbrains.anko.*

class CurrencyHeaderItem(private val vm : RevolutViewModel, viewGroup: ViewGroup) : AnkoCoroutinesLayout<String>(viewGroup) {

    override fun createView(ui: AnkoContext<AnkoCoroutinesLayout<String>>): View =
        ui.frameLayout {
            imageView {
                setImageUrl(itemChanges.map { toImageUrl(it) })
            }.lparams(dip(36), dip(36)) { margin = dip(8) }

            textView {
                padding = dip(8)
                textSizeDimen = R.dimen.text_medium
                textColorResource = android.R.color.black
                setText(itemChanges)
            }.lparams {
                leftMargin = dip(52)
                gravity = Gravity.CENTER_VERTICAL
            }

            textView {
                padding = dip(8)
                textSizeDimen = R.dimen.text_medium
                textColorResource = android.R.color.black
                setText(itemChanges.flatMapConcat { flow { emit(codeToValue(it)) } })
            }.lparams {
                leftMargin = dip(96)
                gravity = Gravity.CENTER_VERTICAL
            }

            editText {
                inputType = InputType.TYPE_CLASS_NUMBER or
                        InputType.TYPE_NUMBER_FLAG_SIGNED or
                        InputType.TYPE_NUMBER_FLAG_DECIMAL
                gravity = Gravity.END
                lines = 1
                maxLines = 1
                filters = arrayOf(rateInputFilter(7, 4))
                setText(vm.currentValue)
                afterTextChanges(vm.currentValue)
            }.lparams(dip(124), wrapContent, Gravity.CENTER_VERTICAL or Gravity.END)
        }.apply {
            layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
        }


}