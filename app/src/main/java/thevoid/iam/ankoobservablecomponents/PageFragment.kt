package thevoid.iam.ankoobservablecomponents

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import iam.thevoid.e.safe
import org.jetbrains.anko.*
import thevoid.iam.components.ext.setText
import thevoid.iam.components.rx.RxField

class PageFragment : Fragment(), AnkoComponent<PageFragment> {

    val obj = RxField<Something>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        context?.let { createView(AnkoContext.create(it, this)) }

    override fun createView(ui: AnkoContext<PageFragment>): View =
        ui.frameLayout {

            button {
                text = "Random Double"
                setOnClickListener { obj.set(Something(ObservableObject.randomString())) }
            }.lparams(wrapContent, wrapContent) {
                gravity = Gravity.CENTER_HORIZONTAL
                topMargin = dip(16)
            }

            textView {
                id = R.id.text
                textSize = dip(16).toFloat()
                setText(obj.observe { it.elem?.data.safe })
            }.lparams {
                gravity = Gravity.CENTER
            }
        }
}

class Something(val data: String)