package iam.thevoid.noxml.coroutines.utils

import android.view.View
import iam.thevoid.e.safe
import iam.thevoid.noxml.binding.Setter
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.*

abstract class CoroutinesSetter<V : View, C>(view: V, private val flow: Flow<C>) :
    Setter<V, C>(view), CoroutineScope by CoroutineScope(Main), FlowCollector<C> {

    private var job: Job? = null

    @OptIn(InternalCoroutinesApi::class)
    override fun subscribeChanges() {
        job?.cancel()
        job = launch { flow.collect(this@CoroutinesSetter) }
    }

    override suspend fun emit(value: C) {
        if (job?.isActive.safe())
            set(viewRef.get(), value)
    }

    override fun unsubscribeChanges() {
        job?.cancel()
    }

    override fun applyChanges() {
        runBlocking {
            set(viewRef.get(), flow.first())
        }
    }

    override fun theSameAs(setter: Setter<*, *>): Boolean =
        setter is CoroutinesSetter && setter.flow === flow
}