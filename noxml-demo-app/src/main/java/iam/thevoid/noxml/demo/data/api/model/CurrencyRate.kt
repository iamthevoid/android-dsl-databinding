package iam.thevoid.noxml.demo.data.api.model

import iam.thevoid.noxml.recycler.DiffCallback
import iam.thevoid.noxml.demo.util.toImageUrl

data class CurrencyRate(
    var code: String,
    var rate: Double
) : DiffCallback.Diffable {
    override fun areItemsTheSame(another: Any?): Boolean =
        another is CurrencyRate && another.code == code

    override fun areContentsTheSame(another: Any?): Boolean =
        another is CurrencyRate && another.rate == rate
                && another.image == image

    val image by lazy { toImageUrl(code) }
}
/*
<div class="currencyFlag" style="background-image: url('https://www.xe.com/themes/xe/images/flags/big/usd.png');"></div>
 */