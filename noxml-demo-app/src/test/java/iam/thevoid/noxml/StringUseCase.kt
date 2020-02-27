package iam.thevoid.noxml

import org.junit.Assert
import org.junit.Test

class StringUseCase  {

    @Test
    fun rateRegex() {
        val regex = Regex("-*\\d{0,7}(\\.\\d{0,4})*")
        Assert.assertTrue("-1".matches(regex))
        Assert.assertTrue("-".matches(regex))
        Assert.assertTrue("-1.0".matches(regex))
        Assert.assertTrue("1".matches(regex))
        Assert.assertTrue("1.34".matches(regex))
        Assert.assertFalse("1.34543".matches(regex))
        Assert.assertFalse("1.3-43".matches(regex))
        Assert.assertFalse("1.3-43".matches(regex))
        Assert.assertTrue("-4342321.343".matches(regex))
        Assert.assertFalse("40342321.343".matches(regex))
    }

}