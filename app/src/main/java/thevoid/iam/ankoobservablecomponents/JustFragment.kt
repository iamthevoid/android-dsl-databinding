package thevoid.iam.ankoobservablecomponents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import iam.thevoid.ankoviews.widget.mvvm.AnkoMvvmFragment
import org.jetbrains.anko.*
import thevoid.iam.components.mvvm.ViewModelBundleProvider
import thevoid.iam.components.mvvm.view.MvvmFragment
import thevoid.iam.components.mvvm.createBundle
import thevoid.iam.components.widget.ext.setBackgroundColor

class JustFragment : AnkoMvvmFragment<JustViewModel>() {

    override fun provideViewModel(): ViewModelBundleProvider = createBundle(JustViewModel::class)

    override fun createView(ui: AnkoContext<AnkoMvvmFragment<JustViewModel>>): View =
        ui.frameLayout {
            padding = dip(3)
            frameLayout {
                setBackgroundColor(viewModel<JustViewModel>().color)
            }
        }
}