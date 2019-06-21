package thevoid.iam.ankoobservablecomponents.data.api.model

import thevoid.iam.ankoobservablecomponents.util.toImageUrl

data class CurrencyRate(
    var code: String,
    var rate: Double
) {
    val image
        get() = toImageUrl(code)
}
/*
<div class="currencyFlag" style="background-image: url('https://www.xe.com/themes/xe/images/flags/big/usd.png');"></div>
 */