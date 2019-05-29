package thevoid.iam.ankoobservablecomponents

import android.view.Gravity
import android.view.View
import iam.thevoid.e.safe
import org.jetbrains.anko.*
import thevoid.iam.components.mvvm.ViewModelBundleProvider
import thevoid.iam.components.mvvm.createBundle
import thevoid.iam.components.mvvm.view.MvvmFragment
import thevoid.iam.components.widget.ext.afterTextChanges
import thevoid.iam.components.widget.ext.onTextChanges
import thevoid.iam.components.widget.ext.setText
import thevoid.iam.components.widget.ext.setTextSilent

class PageFragment : MvvmFragment() {

    override fun createView(ui: AnkoContext<MvvmFragment>): View =
        ui.frameLayout {

            editText {
                hint = "Enter something"
                onTextChanges(viewModel<PageViewModel>().changes)
            }.lparams(matchParent, wrapContent) {
                gravity = Gravity.CENTER_HORIZONTAL
                topMargin = dip(16)
            }

            textView {
                id = R.id.text
                textSize = dip(16).toFloat()
                setText(viewModel<PageViewModel>().changes.observe { it.elem?.s?.toString().safe })
            }.lparams {
                gravity = Gravity.CENTER
            }
        }


    override fun provideViewModel(): ViewModelBundleProvider = createBundle(PageViewModel::class.java)

}