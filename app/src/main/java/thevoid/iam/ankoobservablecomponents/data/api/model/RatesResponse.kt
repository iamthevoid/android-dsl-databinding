package thevoid.iam.ankoobservablecomponents.data.api.model

import com.google.gson.annotations.JsonAdapter
import thevoid.iam.ankoobservablecomponents.data.api.jsonadapter.RatesAdapter
import thevoid.iam.ankoobservablecomponents.data.api.jsonadapter.StringDateAdapter
import java.util.*

data class RatesResponse(
    @JsonAdapter(StringDateAdapter::class)
    val date: Date,

    val base : String,

    @JsonAdapter(RatesAdapter::class)
    val rates : List<CurrencyRate>
)
