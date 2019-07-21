package thevoid.iam.ankoobservablecomponents.ui.mvvm.recycler.items

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import iam.thevoid.ae.setRippleClickAnimation
import iam.thevoid.ankoviews.widget.adapter.AnkoLayout
import iam.thevoid.e.safe
import org.jetbrains.anko.*
import thevoid.iam.ankoobservablecomponents.R
import thevoid.iam.ankoobservablecomponents.data.api.model.CurrencyRate
import thevoid.iam.ankoobservablecomponents.util.codeToValue
import thevoid.iam.ankoobservablecomponents.util.setImageUrl
import thevoid.iam.components.rx.fields.RxString
import thevoid.iam.components.widget.ext.setText
import java.lang.ref.WeakReference

class CurrencySimpleItem(startValue: RxString, viewGroup: ViewGroup) : AnkoLayout<CurrencyRate>(viewGroup) {

    private val current = WeakReference(startValue)

    override fun createView(ui: AnkoContext<AnkoLayout<CurrencyRate>>): View =
        ui.frameLayout {

            setRippleClickAnimation()

            setOnClickListener { item.get()?.code?.also { current.get()?.set(it) } }

            imageView {
                setImageUrl(item.map { it.elem?.image.safe })
            }.lparams(dip(36), dip(36)) { margin = dip(8) }

            textView {
                padding = dip(8)
                textSizeDimen = R.dimen.text_medium
                textColorResource = android.R.color.black
                setText(item.map { it.elem?.code.safe })
            }.lparams {
                leftMargin = dip(52)
                gravity = Gravity.CENTER_VERTICAL
            }

            textView {
                padding = dip(8)
                textSizeDimen = R.dimen.text_medium
                textColorResource = android.R.color.black
                setText(item.map { it.elem?.code.safe }.flatMapSingle { codeToValue(it) })
            }.lparams {
                leftMargin = dip(96)
                gravity = Gravity.CENTER_VERTICAL
            }

            textView {
                padding = dip(8)
                textColorResource = android.R.color.black
                textSizeDimen = R.dimen.text_medium
                setText(item.map { "${it.elem?.rate.safe}" })
            }.lparams {
                gravity = Gravity.END or Gravity.CENTER_VERTICAL
            }
        }.apply {
            layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
        }


}