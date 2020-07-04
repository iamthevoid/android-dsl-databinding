package iam.thevoid.noxml.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class FlowTest {

    val channel = ConflatedBroadcastChannel("Behavior")

    @Test
    fun testLatest() {
        runBlocking {
            val behaviorFlow = behaviorFlow()
            Assert.assertEquals("Factory", next(flowFactory()))
            Assert.assertEquals("Simple", next(simpleFlow()))
            Assert.assertEquals("Behavior", next(behaviorFlow))
            val job  = launch { behaviorFlow.collect { println(it) } }
            channel.send("Another value to test subscription alive")
            job.cancel()
        }
    }

    private fun flowFactory() = flow {
        emit("Factory")
    }

    private fun simpleFlow() = flowOf( "Simple")

    private fun behaviorFlow() : Flow<String> = channel.asFlow()



    private suspend fun <T> next(flow : Flow<T>) : T {
        return flow.first()
    }
}