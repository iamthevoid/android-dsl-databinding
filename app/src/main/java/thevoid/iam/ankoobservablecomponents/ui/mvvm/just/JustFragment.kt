package thevoid.iam.ankoobservablecomponents.ui.mvvm.just

import android.view.View
import iam.thevoid.ankoviews.widget.mvvm.AnkoMvvmFragment
import org.jetbrains.anko.*
import thevoid.iam.components.mvvm.ViewModelBindingProvider
import thevoid.iam.components.mvvm.createBinding
import thevoid.iam.components.widget.ext.setBackgroundColor

class JustFragment : AnkoMvvmFragment<JustViewModel>() {

    override fun provideViewModel(): ViewModelBindingProvider = activity.createBinding(JustViewModel::class)

    override fun createView(ui: AnkoContext<AnkoMvvmFragment<JustViewModel>>): View =
        ui.frameLayout {
            padding = dip(3)
            frameLayout {
                setBackgroundColor(viewModel<JustViewModel>().color)
            }
        }
}