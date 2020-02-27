package iam.thevoid.noxml

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import org.junit.Test

@UseExperimental(ExperimentalCoroutinesApi::class, FlowPreview::class)
class CoroutinesUseCases {


    @Test
    fun testBroadcast() {
        class Element

        val channel = ConflatedBroadcastChannel(1)
        val flow = channel.asFlow()
        runBlocking {
            println("Collecting")
            GlobalScope.launch { flow.collect { println("Caught #1 $it") } }
            GlobalScope.launch { flow.collect { println("Caught #2 $it") } }
            channel.send(2)
            GlobalScope.launch { flow.collect { println("Caught #3 $it") } }
            delay(5000)
        }
    }

}