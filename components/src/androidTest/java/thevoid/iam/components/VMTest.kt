package thevoid.iam.components

import org.junit.Test
import thevoid.iam.components.mvvm.viewmodel.RxViewModel
import thevoid.iam.components.mvvm.createBundle

class VMTest {

    @Test
    fun testCreator() {
        val bundle = createBundle(VM::class to { VM(6) })
        print(bundle)
    }

}

private class VM(val t: Int) : RxViewModel() {

}