package thevoid.iam.ankoobservablecomponents.ui.mvvm.scroll

import iam.thevoid.e.safe
import iam.thevoid.rxe.subscribeSafe
import thevoid.iam.components.mvvm.adapter.ItemBindings
import thevoid.iam.components.mvvm.viewmodel.RxViewModel
import thevoid.iam.components.rx.fields.RxField
import thevoid.iam.components.rx.fields.RxFloat
import thevoid.iam.components.rx.fields.RxList

class ScrollViewModel : RxViewModel() {


    private val image by lazy { "https://xakep.ru/wp-content/uploads/2018/03/162024/Android_DrinkBeer-h.jpg" }

    private val list by lazy { createItems() }

    val items by lazy { RxList(mutableListOf<Any>(image).apply { addAll(list) }) }

    val binding = ItemBindings.of(String::class.java) { ImageItem(it, this) }
        .addBinding(Integer::class.java) { IntItem(it) }

    val scrolled by lazy { RxField<Int>() }

    val scrolledEver by lazy { RxFloat() }

    private fun createItems() = (1..100).map { it }

    override fun onActive() {
        super.onActive()
        scrolledEver.set(0f)
        toDispose(scrolled.observe()
            .subscribeSafe { scrolledEver.set(scrolledEver.get() + it.elem.safe) })
    }
}