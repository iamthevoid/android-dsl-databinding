package iam.thevoid.noxml.demo.ui.mvvm.coroutines

import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import iam.thevoid.noxml.core.mvvm.viewModel
import iam.thevoid.noxml.coroutines.extensions.view.addSetter
import kotlinx.coroutines.flow.*
import org.jetbrains.anko.*
import iam.thevoid.noxml.demo.R
import iam.thevoid.noxml.demo.ui.BaseFragment

class CoroutinesFragment : BaseFragment() {

    val vm by viewModel<CoroutinesViewModel>()

    override fun createView(ui: AnkoContext<BaseFragment>): View =
        ui.frameLayout {
            setBackgroundColor(vm.flw.map {
                val color = Color.parseColor(it)
                Log.i("CoroutinesFragment", "thread = ${Thread.currentThread().name}, fl color = $color")
                color
            })
            textView {
                textSizeDimen = R.dimen.text_extra_large
                setTextColor(Color.BLACK)
                setText(vm.flw.onEach { Log.i("CoroutinesFragment", "thread = ${Thread.currentThread().name}, tv text = $it") })
            }.lparams(wrapContent, wrapContent, Gravity.CENTER)
        }

    fun FrameLayout.setBackgroundColor(flow : Flow<Int>) =
        addSetter(flow) { backgroundColor = it }

    fun TextView.setText(flow : Flow<String>) =
        addSetter(flow) { text = it }

    fun TextView.setTextColor(flow : Flow<Int>) =
        addSetter(flow) { textColor = it }

}