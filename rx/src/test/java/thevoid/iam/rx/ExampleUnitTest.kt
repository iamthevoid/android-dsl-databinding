package thevoid.iam.rx

import org.junit.Test

import org.junit.Assert.*
import thevoid.iam.rx.adapter.RxPagerAdapter
import thevoid.iam.rx.widget.util.key

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun <T : Number> testStack() {
        print(key())
    }

    @Test
    fun testMapper() {
        val list = RxPagerAdapter.fromTitlesAndItems(listOf(1,2,3), listOf("One", "Two"))
        print(list)
    }
}