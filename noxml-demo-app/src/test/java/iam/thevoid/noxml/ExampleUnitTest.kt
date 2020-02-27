package iam.thevoid.noxml

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    class Observable : Object() {
        override fun finalize() {
            super.finalize()
            println("destroyed")
        }
    }

    @Test
    fun testReference() {
        var a : Any? = Observable()

        println("before nulling a. a = $a")

        a = null

        println("before gc a = $a")

        System.gc()

        println("after gc")

        runBlocking { delay(20) }

        println("after #1 delay")

        runBlocking { delay(2000) }

        println("after #2 delay")
    }
}
