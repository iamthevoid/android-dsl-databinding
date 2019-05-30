package thevoid.iam.components.widget.ext

import androidx.viewpager.widget.ViewPager
import io.reactivex.Flowable
import thevoid.iam.components.rx.fields.RxInt

fun ViewPager.setCurrentPage(page: Flowable<Int>, smoothScroll: Boolean = true) =
    addSetter(page) { setCurrentItem(it, smoothScroll) }

fun ViewPager.setCurrentPage(page: RxInt, smoothScroll: Boolean = true) =
    addSetter(page.observe()) { setCurrentItem(it, smoothScroll) }