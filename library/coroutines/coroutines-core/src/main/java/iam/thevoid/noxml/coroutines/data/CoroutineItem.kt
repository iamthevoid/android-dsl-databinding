@file:Suppress("unused")

package iam.thevoid.noxml.coroutines.data

import iam.thevoid.e.safe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

@Deprecated("Items will be removed in release version, use realization with FlowableProcessor instead")
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
open class CoroutineItem<T>(item: T, private val onChange: (T) -> Unit = {}) {

    private val channel = ConflatedBroadcastChannel(item)

    fun set(item: T) {
        channel.offer(item)
        onChange(item)
    }

    fun get() = channel.value

    fun observe() =  channel.asFlow()

    fun <E> map(mapper: (T) -> E): Flow<E> = observe().map { mapper(it) }

    fun <E> mapSelf(mapper: T.() -> E): Flow<E> = observe().map { it.mapper() }
}

@Deprecated("Items will be removed in release version, use realization with FlowableProcessor instead")
@Suppress("UNCHECKED_CAST")
open class CoroutineCharSequence<T : CharSequence>(initial: T = "" as T, onChange: (T) -> Unit = {}) :
    CoroutineItem<T>(initial, onChange)

@Deprecated("Items will be removed in release version, use realization with FlowableProcessor instead")
class CoroutineBoolean(initial: Boolean = false, onChange: (Boolean) -> Unit = {}) :
    CoroutineItem<Boolean>(initial, onChange) {

    fun not() = observe().map { !it }
}

@Deprecated("Items will be removed in release version, use realization with FlowableProcessor instead")
class CoroutineFloat(initial: Float = 0f, onChange: (Float) -> Unit = {}) :
    CoroutineItem<Float>(initial, onChange)

@Deprecated("Items will be removed in release version, use realization with FlowableProcessor instead")
class CoroutineDouble(initial: Double = .0, onChange: (Double) -> Unit = {}) :
    CoroutineItem<Double>(initial, onChange)

@Deprecated("Items will be removed in release version, use realization with FlowableProcessor instead")
class CoroutineInt(initial: Int = 0, onChange: (Int) -> Unit = {}) : CoroutineItem<Int>(initial, onChange)

@Deprecated("Items will be removed in release version, use realization with FlowableProcessor instead")
class CoroutineLong(initial: Long = 0L, onChange: (Long) -> Unit = {}) : CoroutineItem<Long>(initial, onChange)

@Deprecated("Items will be removed in release version, use realization with FlowableProcessor instead")
class CoroutineString(string: String = "", onChange: (String) -> Unit = {}) :
    CoroutineCharSequence<String>(string, onChange)

@Deprecated("Items will be removed in release version, use realization with FlowableProcessor instead")
class CoroutineList<T>(initial: List<T> = emptyList(), onChange: (List<T>) -> Unit = {}) :
    CoroutineItem<List<T>>(initial, onChange) {

    fun add(item: T) = set(get().toMutableList().apply { add(item) })

    fun add(items: List<T>) = set(get() + items)

    fun replace(predicate: (T) -> Boolean, item: T) {
        val list = get().toMutableList()
        val found = list.find(predicate)
        val indexOfFound = list.indexOf(found)
        if (indexOfFound == -1 || found == null) return
        list[indexOfFound] = item
        set(list)
    }

    fun update(predicate: (T) -> Boolean, change: (T) -> Unit) {
        val list = get().toMutableList()
        val found = list.find(predicate)
        val indexOfFound = list.indexOf(found)
        if (indexOfFound == -1 || found == null) return
        list[indexOfFound] = found.also {
            change(it)
        }
        set(list)
    }

    fun remove(predicate: (T) -> Boolean) {
        val list = ArrayList(get().safe())
        val found = list.find(predicate)
        val indexOfFound = list.indexOf(found)
        if (indexOfFound == -1 || found == null) return
        list.removeAt(indexOfFound)
        set(list)
    }

    fun isEmpty() = get().isEmpty()
}