package thevoid.iam.ankoobservablecomponents.ui.mvvm.recycler.items

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import iam.thevoid.ankoviews.widget.adapter.AnkoLayout
import iam.thevoid.e.safe
import org.jetbrains.anko.*
import thevoid.iam.ankoobservablecomponents.R
import thevoid.iam.ankoobservablecomponents.util.codeToValue
import thevoid.iam.ankoobservablecomponents.util.setImageUrl
import thevoid.iam.ankoobservablecomponents.util.toImageUrl
import thevoid.iam.components.widget.ext.setText

class CurrencyHeaderItem(viewGroup: ViewGroup) : AnkoLayout<String>(viewGroup) {

    override fun createView(ui: AnkoContext<AnkoLayout<String>>): View =
        ui.frameLayout {
            imageView {
                setImageUrl(item.map { toImageUrl(it.elem.safe) })
            }.lparams(dip(36), dip(36)) { margin = dip(8) }

            textView {
                padding = dip(8)
                textSizeDimen = R.dimen.text_medium
                textColorResource = android.R.color.black
                setText(item.map { item.get().safe })
            }.lparams {
                leftMargin = dip(52)
                gravity = Gravity.CENTER_VERTICAL
            }

            textView {
                padding = dip(8)
                textSizeDimen = R.dimen.text_medium
                textColorResource = android.R.color.black
                setText(item.map { it.elem.safe }.flatMapSingle { codeToValue(it) })
            }.lparams {
                leftMargin = dip(96)
                gravity = Gravity.CENTER_VERTICAL
            }
        }


}