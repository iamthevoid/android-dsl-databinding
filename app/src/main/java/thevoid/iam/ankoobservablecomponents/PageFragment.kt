package thevoid.iam.ankoobservablecomponents

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.jetbrains.anko.*
import thevoid.iam.components.ext.setText
import thevoid.iam.components.rx.RxDouble
import java.util.*

class PageFragment : Fragment(), AnkoComponent<PageFragment> {

    val double = RxDouble()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        context?.let { createView(AnkoContext.create(it, this)) }

    override fun createView(ui: AnkoContext<PageFragment>): View =
        ui.frameLayout {

            button {
                text = "Random Double"
                setOnClickListener { double.set(Random().nextDouble()) }
            }.lparams(wrapContent, wrapContent) {
                gravity = Gravity.CENTER_HORIZONTAL
                topMargin = dip(16)
            }

            textView {
                id = R.id.text
                textSize = dip(16).toFloat()
                setText(double, 3)
            }.lparams {
                gravity = Gravity.CENTER
            }
        }
}