package iam.thevoid.noxml.demo.data.api.model

import com.google.gson.annotations.JsonAdapter
import iam.thevoid.noxml.demo.data.api.jsonadapter.RatesAdapter
import iam.thevoid.noxml.demo.data.api.jsonadapter.StringDateAdapter
import java.util.*

data class RatesResponse(
    @JsonAdapter(StringDateAdapter::class)
    val date: Date,

    val base : String,

    @JsonAdapter(RatesAdapter::class)
    val rates : List<CurrencyRate>
)
