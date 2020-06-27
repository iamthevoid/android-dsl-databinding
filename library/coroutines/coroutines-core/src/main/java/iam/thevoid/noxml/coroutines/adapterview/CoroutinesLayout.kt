package iam.thevoid.noxml.coroutines.adapterview

import android.view.ViewGroup
import iam.thevoid.noxml.adapterview.Layout
import iam.thevoid.noxml.coroutines.data.CoroutineField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
abstract class CoroutinesLayout<T>(parent: ViewGroup) : Layout<T>(parent), CoroutineScope by CoroutineScope(Dispatchers.IO)  {

    private val itemFactory: () -> CoroutineField<T> = { CoroutineField() }

    override fun set(item: T) {
        launch { this@CoroutinesLayout.item.set(item) }
    }

    @Deprecated("Became private in next version, for get item use fun getItem() instead")
    val item: CoroutineField<T> by lazy(itemFactory)

    val itemChanges: Flow<T> by lazy {
        item.onlyPresent()
    }

    fun getItem() : T? = item.get()

    fun changeItem(itemChange: T?.() -> Unit) {
        launch {
            item.set(item.get().apply {
                itemChange()
            })
        }
    }
}