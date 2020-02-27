package iam.thevoid.noxml.demo.ui.mvvm.revolut.items

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import iam.thevoid.ae.setRippleClickAnimation
import iam.thevoid.noxml.demo.R
import iam.thevoid.noxml.demo.data.api.model.CurrencyRate
import iam.thevoid.noxml.demo.ui.mvvm.revolut.RevolutViewModel
import iam.thevoid.noxml.demo.util.codeToValue
import iam.thevoid.noxml.demo.util.setImageUrl
import iam.thevoid.noxml.anko.coroutines.AnkoCoroutinesLayout
import iam.thevoid.noxml.coroutines.extensions.setText
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.jetbrains.anko.*

class CurrencySimpleItem(private val vm : RevolutViewModel, viewGroup: ViewGroup) : AnkoCoroutinesLayout<CurrencyRate>(viewGroup) {

    override fun createView(ui: AnkoContext<AnkoCoroutinesLayout<CurrencyRate>>): View =
        ui.frameLayout {

            setRippleClickAnimation()

            setOnClickListener { vm.updateCurrent(item.get()) }

            imageView {
                setImageUrl(itemChanges.map { it.image })
            }.lparams(dip(36), dip(36)) { margin = dip(8) }

            textView {
                padding = dip(8)
                textSizeDimen = R.dimen.text_medium
                textColorResource = android.R.color.black
                setText(itemChanges.map { it.code })
            }.lparams {
                leftMargin = dip(52)
                gravity = Gravity.CENTER_VERTICAL
            }

            textView {
                padding = dip(8)
                textSizeDimen = R.dimen.text_medium
                textColorResource = android.R.color.black
                setText(itemChanges.map { it.code }.flatMapConcat { flow { emit(codeToValue(it)) } })
            }.lparams {
                leftMargin = dip(96)
                gravity = Gravity.CENTER_VERTICAL
            }

            textView {
                padding = dip(8)
                textColorResource = android.R.color.black
                textSizeDimen = R.dimen.text_medium
                setText(combine(
                    itemChanges.map { it.rate },
                    vm.currentValue.observe(),
                    vm::resultValue
                ))
            }.lparams {
                gravity = Gravity.END or Gravity.CENTER_VERTICAL
            }
        }.apply {
            layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
        }


}