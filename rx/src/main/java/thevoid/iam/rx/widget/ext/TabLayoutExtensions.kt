package thevoid.iam.rx.widget.ext

import com.google.android.material.tabs.TabLayout
import iam.thevoid.common.adapter.adapters.OnTabSelectedListenerAdapter
import iam.thevoid.common.adapter.delegate.OnTabSelectedListenerDelegate
import io.reactivex.Flowable
import thevoid.iam.rx.R
import thevoid.iam.rx.rxdata.fields.RxField
import thevoid.iam.rx.rxdata.fields.RxItem

private val TabLayout.onTabSelectListener: OnTabSelectedListenerDelegate
    get() = ((getTag(R.id.onTabSelectListener) as? OnTabSelectedListenerDelegate)
        ?: OnTabSelectedListenerDelegate().also {
            setTag(R.id.onTabSelectListener, it)
            addOnTabSelectedListener(it)
        })

fun TabLayout.selectTab(tab: TabLayout.Tab?) {
    post { tab?.select() }
}

fun TabLayout.selectTab(tabFlowable: Flowable<Int>) =
    selectTab(tabFlowable) { it }

fun <T : Any> TabLayout.selectTab(tabFlowable: Flowable<T>, mapper: (T) -> Int) =
    addSetter(tabFlowable) {
        val tabSelectedListener = onTabSelectListener
        removeOnTabSelectedListener(tabSelectedListener)
        selectTab(getTabAt(mapper(it)))
        addOnTabSelectedListener(tabSelectedListener)
    }


fun TabLayout.onTabReselect(rxTab: RxField<TabLayout.Tab>) =
    onTabReselect(rxTab) { it }

fun <T : Any> TabLayout.onTabReselect(rxField: RxField<T>, mapper : ((TabLayout.Tab?) -> T?)) =
    addGetter({
        onTabSelectListener.addOnTabReselectCallback(object : OnTabSelectedListenerAdapter() {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                it.invoke(mapper(tab))
            }
        })
    }, rxField)

fun <T : Any> TabLayout.onTabReselect(rxItem: RxItem<T>, mapper : ((TabLayout.Tab?) -> T)) =
    addGetter({
        onTabSelectListener.addOnTabReselectCallback(object : OnTabSelectedListenerAdapter() {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.also { nonNullTab -> it.invoke(mapper(nonNullTab)) }
            }
        })
    }, rxItem)


fun TabLayout.onTabUnselected(rxTab: RxField<TabLayout.Tab>) =
    onTabUnselected(rxTab) { it }

fun <T : Any> TabLayout.onTabUnselected(rxField: RxField<T>, mapper : ((TabLayout.Tab?) -> T?)) =
    addGetter({
        onTabSelectListener.addOnTabUnselectedCallback(object : OnTabSelectedListenerAdapter() {
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                it.invoke(mapper(tab))
            }
        })
    }, rxField)

fun <T : Any> TabLayout.onTabUnselected(rxItem: RxItem<T>, mapper : ((TabLayout.Tab?) -> T)) =
    addGetter({
        onTabSelectListener.addOnTabUnselectedCallback(object : OnTabSelectedListenerAdapter() {
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.also { nonNullTab -> it.invoke(mapper(nonNullTab)) }
            }
        })
    }, rxItem)


fun TabLayout.onTabSelected(rxTab: RxField<TabLayout.Tab>) =
    onTabSelected(rxTab) { it }

fun <T : Any> TabLayout.onTabSelected(rxField: RxField<T>, mapper : ((TabLayout.Tab?) -> T?)) =
    addGetter({
        onTabSelectListener.addOnTabSelectedCallback(object : OnTabSelectedListenerAdapter() {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                it.invoke(mapper(tab))
            }
        })
    }, rxField)

fun <T : Any> TabLayout.onTabSelected(rxItem: RxItem<T>, mapper : ((TabLayout.Tab?) -> T)) =
    addGetter({
        onTabSelectListener.addOnTabSelectedCallback(object : OnTabSelectedListenerAdapter() {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.also { nonNullTab -> it.invoke(mapper(nonNullTab)) }
            }
        })
    }, rxItem)