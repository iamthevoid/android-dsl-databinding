package iam.thevoid.noxml.demo.ui.mvvm.revolut.items

import android.graphics.Typeface
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import iam.thevoid.noxml.anko.rx.AnkoRxLayout
import iam.thevoid.noxml.demo.R
import iam.thevoid.noxml.demo.ui.mvvm.revolut.CurrencyViewModel
import iam.thevoid.noxml.demo.util.codeToValue
import iam.thevoid.noxml.demo.util.rateInputFilter
import iam.thevoid.noxml.demo.util.setImageUrl
import iam.thevoid.noxml.demo.util.toImageUrl
import iam.thevoid.noxml.rx2.extensions.edittext.setText
import iam.thevoid.noxml.rx2.extensions.textview.afterTextChanges
import iam.thevoid.noxml.rx2.extensions.textview.setText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.jetbrains.anko.*

class CurrencyHeaderItem(private val vm : CurrencyViewModel, viewGroup: ViewGroup) : AnkoRxLayout<String>(viewGroup) {

    override fun createView(ui: AnkoContext<AnkoRxLayout<String>>): View =
        ui.frameLayout {
            imageView {
                setImageUrl(itemChanges.map { toImageUrl(it).apply { println(this) } })
            }.lparams(dip(36), dip(36)) { margin = dip(8) }

            textView {
                setText(itemChanges)

                // params
                padding = dip(8)
                textSizeDimen = R.dimen.text_medium
                textColorResource = android.R.color.black
            }.lparams {
                leftMargin = dip(52)
                gravity = Gravity.CENTER_VERTICAL
            }

            textView {
                setText(itemChanges.flatMapSingle(::codeToValue).startWith("---"))

                // params
                padding = dip(8)
                textSizeDimen = R.dimen.text_medium
                textColorResource = android.R.color.black
            }.lparams {
                leftMargin = dip(96)
                gravity = Gravity.CENTER_VERTICAL
            }

            editText {
                setText(vm.currentValue)
                afterTextChanges(vm.currentValue)

                // params
                inputType = InputType.TYPE_CLASS_NUMBER or
                        InputType.TYPE_NUMBER_FLAG_SIGNED or
                        InputType.TYPE_NUMBER_FLAG_DECIMAL
                gravity = Gravity.END
                lines = 1
                maxLines = 1
                filters = arrayOf(rateInputFilter(7, 4))
            }.lparams(dip(124), wrapContent, Gravity.CENTER_VERTICAL or Gravity.END)
        }.apply {
            layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
        }
}