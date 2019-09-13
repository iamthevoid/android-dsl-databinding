package thevoid.iam.ankoobservablecomponents.ui.mvvm.recycler.items

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import iam.thevoid.ankorx.AnkoLayout
import org.jetbrains.anko.*
import thevoid.iam.ankoobservablecomponents.R
import thevoid.iam.ankoobservablecomponents.util.codeToValue
import thevoid.iam.ankoobservablecomponents.util.setImageUrl
import thevoid.iam.ankoobservablecomponents.util.toImageUrl
import thevoid.iam.rx.widget.ext.setText

class CurrencyHeaderItem(viewGroup: ViewGroup) : AnkoLayout<String>(viewGroup) {

    override fun createView(ui: AnkoContext<AnkoLayout<String>>): View =
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
                setText(itemChanges.flatMapSingle { codeToValue(it) })
            }.lparams {
                leftMargin = dip(96)
                gravity = Gravity.CENTER_VERTICAL
            }
        }


}