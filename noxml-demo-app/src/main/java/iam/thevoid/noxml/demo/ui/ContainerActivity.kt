package iam.thevoid.noxml.demo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import iam.thevoid.noxml.demo.ui.mvvm.coroutines.CoroutinesFragment
import iam.thevoid.noxml.demo.ui.mvvm.PagerFragment
import iam.thevoid.noxml.demo.ui.mvvm.page.PageFragment
import iam.thevoid.noxml.demo.ui.mvvm.pagination.PaginationLoaderFragment
import iam.thevoid.noxml.demo.ui.mvvm.revolut.CurrenciesFragment
import iam.thevoid.noxml.demo.ui.mvvm.scroll.PagerCoordinatorFragment

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
                    Item.COROUTINES_TEST -> CoroutinesFragment()
                    Item.PAGER -> PagerFragment()
                    Item.PAGER_COORDINATOR -> PagerCoordinatorFragment()
                    Item.CURRENCY_LIST -> CurrenciesFragment()
                    Item.PAGINTION_LOADER -> PaginationLoaderFragment()
                    Item.TWO_WAY_BINDING -> PageFragment()
                    else -> throw IllegalArgumentException()
                }, "Fragment"
            )
            .commit()
    }

}