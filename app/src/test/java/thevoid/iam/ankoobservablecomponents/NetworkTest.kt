package thevoid.iam.ankoobservablecomponents

import iam.thevoid.rxe.zip
import org.junit.Test
import thevoid.iam.ankoobservablecomponents.util.codeToValue

class NetworkTest {

    @Test
    fun testCurrencyInfo() {
        val codes = listOf("usd", "eur", "gbp")
        val value = zip(
            codeToValue(codes[0]),
            codeToValue(codes[1]),
            codeToValue(codes[2])
        ) { v1, v2, v3 -> Triple(v1, v2, v3) }.blockingGet()
        println(value)
    }
}