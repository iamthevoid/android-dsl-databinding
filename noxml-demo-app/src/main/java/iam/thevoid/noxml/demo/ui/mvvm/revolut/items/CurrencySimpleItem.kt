package iam.thevoid.noxml.demo.ui.mvvm.revolut.items

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import iam.thevoid.ae.setRippleClickAnimation
import iam.thevoid.noxml.demo.R
import iam.thevoid.noxml.demo.data.api.model.CurrencyRate
import iam.thevoid.noxml.demo.ui.mvvm.revolut.CurrencyViewModel
import iam.thevoid.noxml.demo.util.codeToValue
import iam.thevoid.noxml.demo.util.setImageUrl
import iam.thevoid.noxml.anko.coroutines.AnkoCoroutinesLayout
import iam.thevoid.noxml.anko.rx.AnkoRxLayout
import iam.thevoid.noxml.coroutines.extensions.textview.setText
import iam.thevoid.noxml.rx2.extensions.textview.setText
import io.reactivex.rxkotlin.Flowables
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import org.jetbrains.anko.*

class CurrencySimpleItem(private val vm : CurrencyViewModel, viewGroup: ViewGroup) : AnkoRxLayout<CurrencyRate>(viewGroup) {

    override fun createView(ui: AnkoContext<AnkoRxLayout<CurrencyRate>>): View =
        ui.frameLayout {

            setOnClickListener { getItem()?.also(vm::updateCurrent) }

            imageView {
                setImageUrl(itemChanges.map { it.image })
            }.lparams(dip(36), dip(36)) { margin = dip(8) }

            textView {
                setText(itemChanges.map { it.code })

                // params
                padding = dip(8)
                textSizeDimen = R.dimen.text_medium
                textColorResource = android.R.color.black
            }.lparams {
                leftMargin = dip(52)
                gravity = Gravity.CENTER_VERTICAL
            }

            textView {
                setText(itemChanges.flatMapSingle { codeToValue(it.code) }.startWith("---"))

                // params
                padding = dip(8)
                textSizeDimen = R.dimen.text_medium
                textColorResource = android.R.color.black
            }.lparams {
                leftMargin = dip(96)
                gravity = Gravity.CENTER_VERTICAL
            }

            textView {
                setText(
                    Flowables.combineLatest(
                        itemChanges.map { it.rate },
                        vm.currentValue,
                        vm::resultValue
                    )
                )

                // params
                padding = dip(8)
                textColorResource = android.R.color.black
                textSizeDimen = R.dimen.text_medium
            }.lparams {
                gravity = Gravity.END or Gravity.CENTER_VERTICAL
            }
        }.apply {
            layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
        }
}