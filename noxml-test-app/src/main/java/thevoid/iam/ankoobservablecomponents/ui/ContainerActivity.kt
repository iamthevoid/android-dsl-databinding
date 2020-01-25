package thevoid.iam.ankoobservablecomponents.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import iam.thevoid.ae.view
import iam.thevoid.common.viewModel
import org.jetbrains.anko.frameLayout
import thevoid.iam.ankoobservablecomponents.R
import thevoid.iam.ankoobservablecomponents.ui.mvvm.PagerFragment
import thevoid.iam.ankoobservablecomponents.ui.mvvm.page.PageFragment
import thevoid.iam.ankoobservablecomponents.ui.mvvm.pagination.PaginationLoaderFragment
import thevoid.iam.ankoobservablecomponents.ui.mvvm.recycler.RecyclerFragment
import thevoid.iam.ankoobservablecomponents.ui.mvvm.scroll.PagerCoordinatorFragment

class ContainerActivity : AppCompatActivity() {

    companion object {
        const val ITEM = "ITEM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val item: Item = Item.valueOf(intent.getStringExtra(ITEM))
        supportFragmentManager
            .beginTransaction()
            .replace(
                android.R.id.content, when (item) {
                    Item.PAGER -> PagerFragment()
                    Item.PAGER_COORDINATOR -> PagerCoordinatorFragment()
                    Item.REVOLUT_APP -> RecyclerFragment()
                    Item.PAGINTION_LOADER -> PaginationLoaderFragment()
                    Item.TWO_WAY_BINDING -> PageFragment()
                    else -> throw IllegalArgumentException()
                }, "Fragment"
            )
            .commit()
    }

}