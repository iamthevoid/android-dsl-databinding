package thevoid.iam.ankoobservablecomponents.data.api.jsonadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import thevoid.iam.ankoobservablecomponents.data.api.model.CurrencyRate
import java.io.IOException

class RatesAdapter : TypeAdapter<List<CurrencyRate>>() {

    /**
     * This adapter always used to read value, but never to write it.
     */
    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: List<CurrencyRate>?) {
        if (value == null) {
            out.nullValue()
            return
        }

        // TODO implement convert back to map if need
        out.value(value.toString())
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): List<CurrencyRate>? {
        if (`in`.peek() == JsonToken.NULL) {
            `in`.nextNull()
            return null
        }

        val output = ArrayList<CurrencyRate>()

        `in`.beginObject()
        while (`in`.hasNext()) {
            output.add(readRate(`in`))
        }
        `in`.endObject()

        return output
    }

    private fun readRate(jsonReader: JsonReader): CurrencyRate {
        return CurrencyRate(
            jsonReader.nextName(),
            jsonReader.nextDouble()
        )
    }
}