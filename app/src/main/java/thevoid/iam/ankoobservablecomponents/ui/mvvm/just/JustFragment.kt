package thevoid.iam.ankoobservablecomponents.ui.mvvm.just

import android.view.View
import iam.thevoid.ankoviews.widget.mvvm.AnkoMvvmFragment
import org.jetbrains.anko.*
import iam.thevoid.common.ViewModelBindingProvider
import iam.thevoid.common.createBinding
import thevoid.iam.rx.widget.ext.setBackgroundColor

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