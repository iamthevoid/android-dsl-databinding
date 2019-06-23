package thevoid.iam.components

import org.junit.Test
import thevoid.iam.components.mvvm.viewmodel.RxViewModel

class VMTest {

    @Test
    fun testCreator() {
//        val binding = createBinding(VM::class to { VM(6) })
//        print(binding)
    }

}

private class VM(val t: Int) : RxViewModel() {

}