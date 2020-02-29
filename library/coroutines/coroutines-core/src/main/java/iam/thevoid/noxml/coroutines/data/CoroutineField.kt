package iam.thevoid.noxml.coroutines.data

import iam.thevoid.e.safe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

@UseExperimental(ExperimentalCoroutinesApi::class, FlowPreview::class)
open class CoroutineField<T>(item: T? = null, private val onChange: (T) -> Unit = {}) {

    private val channel = item?.let(::ConflatedBroadcastChannel) ?: ConflatedBroadcastChannel()

    fun set(item: T) {
        channel.offer(item)
        onChange(item)
    }

    fun get() = channel.value

    fun observe() =  channel.asFlow()

    fun <E> map(mapper: (T) -> E): Flow<E> = observe().map { mapper(it) }

    fun <E> mapSelf(mapper: T.() -> E): Flow<E> = observe().map { it.mapper() }
}

open class CoroutineItem<T>(initial: T, onChange: (T) -> Unit = {}) :
    CoroutineField<T>(initial, onChange)

open class CoroutineCharSequence<T : CharSequence>(initial: T = "" as T, onChange: (T) -> Unit = {}) :
    CoroutineItem<T>(initial, onChange)

class CoroutineBoolean(initial: Boolean = false, onChange: (Boolean) -> Unit = {}) :
    CoroutineItem<Boolean>(initial, onChange) {

    fun not() = observe().map { !it }
}

class CoroutineFloat(initial: Float = 0f, onChange: (Float) -> Unit = {}) :
    CoroutineItem<Float>(initial, onChange)

class CoroutineDouble(initial: Double = .0, onChange: (Double) -> Unit = {}) :
    CoroutineItem<Double>(initial, onChange)

class CoroutineInt(initial: Int = 0, onChange: (Int) -> Unit = {}) : CoroutineItem<Int>(initial, onChange)

class CoroutineLong(initial: Long = 0L, onChange: (Long) -> Unit = {}) : CoroutineItem<Long>(initial, onChange)

class CoroutineString(string: String = "", onChange: (String) -> Unit = {}) :
    CoroutineCharSequence<String>(string, onChange)

class CoroutineList<T>(initial: List<T> = emptyList(), onChange: (List<T>) -> Unit = {}) :
    CoroutineItem<List<T>>(initial, onChange) {

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