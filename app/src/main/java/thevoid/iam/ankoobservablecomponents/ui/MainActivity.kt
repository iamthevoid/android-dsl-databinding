package thevoid.iam.ankoobservablecomponents.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.support.v4.viewPager
import thevoid.iam.ankoobservablecomponents.R
import thevoid.iam.ankoobservablecomponents.ui.mvvm.just.JustFragment
import thevoid.iam.ankoobservablecomponents.ui.mvvm.page.PageFragment
import thevoid.iam.ankoobservablecomponents.ui.mvvm.recycler.RecyclerFragment
import thevoid.iam.ankoobservablecomponents.ui.mvvm.scroll.ScrollActivity
import thevoid.iam.components.widget.ext.setUntitledFactories

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frameLayout {
            viewPager {
                id = R.id.pager
                setUntitledFactories(
                    listOf(
                        { RecyclerFragment() },
                        { JustFragment() },
                        { JustFragment() },
                        { PageFragment() }
                    )
                )
            }
        }
    }
}
