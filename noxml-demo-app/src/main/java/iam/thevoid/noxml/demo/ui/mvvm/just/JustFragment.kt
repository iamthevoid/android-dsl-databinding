package iam.thevoid.noxml.demo.ui.mvvm.just

import android.view.View
import iam.thevoid.noxml.anko.mvvm.AnkoMvvmFragment
import iam.thevoid.noxml.core.mvvm.ViewModelBindingProvider
import iam.thevoid.noxml.core.mvvm.createBinding
import iam.thevoid.noxml.rx2.extensions.view.setBackgroundColor
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.dip
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.padding

class JustFragment : AnkoMvvmFragment<JustViewModel>() {

    override fun provideViewModel(): ViewModelBindingProvider = activity.createBinding(JustViewModel::class)

    override fun createView(ui: AnkoContext<AnkoMvvmFragment<JustViewModel>>): View =
        ui.frameLayout {
            padding = dip(3)
            frameLayout {
                setBackgroundColor(vm.color)
            }
        }
}