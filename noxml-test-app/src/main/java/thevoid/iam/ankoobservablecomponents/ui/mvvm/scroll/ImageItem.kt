package thevoid.iam.ankoobservablecomponents.ui.mvvm.scroll

import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.*
import thevoid.iam.ankoobservablecomponents.util.setImageUrl

class ImageItem(viewGroup: ViewGroup) : iam.thevoid.ankorx.AnkoLayout<String>(viewGroup) {
    override fun createView(ui: AnkoContext<iam.thevoid.ankorx.AnkoLayout<String>>): View =
        ui.frameLayout {
            imageView {
                setImageUrl(item.onlyPresent())
            }.lparams(matchParent, wrapContent)
        }.apply {
            layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
        }
}