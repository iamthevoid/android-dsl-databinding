package thevoid.iam.ankoobservablecomponents.ui.mvvm

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.*
import thevoid.iam.ankoobservablecomponents.ui.MainActivity
import thevoid.iam.ankoobservablecomponents.ui.mvvm.scroll.ScrollActivity
import thevoid.iam.ankoobservablecomponents.ui.mvvm.serial.ButikActivity

class SelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frameLayout {
            verticalLayout {
                button {
                    padding = dip(8)
                    text = "Coordinator + pager"
                    singleLine = true
                    allCaps = true
                    setOnClickListener { startActivity(intentFor<ScrollActivity>()) }
                }.lparams(wrapContent, wrapContent)
                button {
                    allCaps = true
                    text = "Pager Activity"
                    padding = dip(8)
                    setOnClickListener { startActivity(intentFor<MainActivity>()) }
                }.lparams(matchParent, wrapContent) {
                    topMargin = dip(8)
                }
                button {
                    allCaps = true
                    text = "Butik Activity"
                    padding = dip(8)
                    setOnClickListener { startActivity(intentFor<ButikActivity>()) }
                }.lparams(matchParent, wrapContent) {
                    topMargin = dip(8)
                }
            }.lparams(wrapContent, wrapContent, Gravity.CENTER)
        }
    }
}
