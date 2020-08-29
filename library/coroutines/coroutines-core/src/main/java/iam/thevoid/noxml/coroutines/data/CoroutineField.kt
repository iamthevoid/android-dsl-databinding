@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package iam.thevoid.noxml.coroutines.data

import iam.thevoid.util.Optional
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@Deprecated("Fields will be removed in major version, use realization with FlowableProcessor instead")
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
open class CoroutineField<T>(item: T? = null, private val onChange: (T?) -> Unit = {}) {

    private val channel = ConflatedBroadcastChannel<Optional<T>>(Optional.of(item))

    fun set(item: T?) {
        channel.offer(Optional.of(item))
        onChange(item)
    }

    fun get() = channel.value.item

    fun observe() =  channel.asFlow()

    fun onlyPresent() =  observe().filter { it.item != null }.map { it.item!! }

    fun <E> map(mapper: (T?) -> E): Flow<E> = observe().map { mapper(it.item) }

    fun <E> mapSelf(mapper: T?.() -> E): Flow<E> = observe().map { it.item.mapper() }
}

