package iam.thevoid.noxml.coroutines

import android.util.Log
import android.view.View
import iam.thevoid.noxml.binding.Setter
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

abstract class CoroutinesSetter<V : View, C>(view: V, private val flow: Flow<C>) : Setter<V, C>(view), CoroutineScope by CoroutineScope(Dispatchers.IO) {

    private var job: Job? = null

    @UseExperimental(InternalCoroutinesApi::class)
    override fun subscribeChanges() {
       job = launch {
            Log.i("Setter", "$flow")
            flow.collect(object : FlowCollector<C> {
                override suspend fun emit(value: C) {
                    launch(Main) { set(viewRef.get(), value) }
                }
            })
        }
    }

    override fun unsubscribeChanges() {
        job?.cancel()
    }

    override fun theSameAs(setter: Setter<*, *>): Boolean =
        setter is CoroutinesSetter && setter.flow === flow
}