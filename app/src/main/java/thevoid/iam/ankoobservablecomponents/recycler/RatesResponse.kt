package thevoid.iam.ankoobservablecomponents.recycler

import com.google.gson.annotations.JsonAdapter
import java.util.*

data class RatesResponse(
    @JsonAdapter(StringDateAdapter::class)
    val date: Date,

    val base : String,

    @JsonAdapter(RatesAdapter::class)
    val rates : List<CurrencyRate>
)
