package thevoid.iam.ankoobservablecomponents.ui.mvvm.scroll

import android.view.View
import android.view.ViewGroup
import iam.thevoid.ankoviews.widget.adapter.AnkoLayout
import iam.thevoid.e.safe
import org.jetbrains.anko.*
import thevoid.iam.ankoobservablecomponents.util.setImageUrl
import thevoid.iam.components.widget.ext.setTranslationY

class ImageItem(viewGroup: ViewGroup, val vm: ScrollViewModel) : AnkoLayout<String>(viewGroup) {
    override fun createView(ui: AnkoContext<AnkoLayout<String>>): View =
        ui.frameLayout {
            imageView {
                setTranslationY(vm.scrolledEver.observe { this / 2 })
                setImageUrl(item.observe { it.elem.safe })
            }.lparams(matchParent, wrapContent)
        }.apply {
            layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
        }
}