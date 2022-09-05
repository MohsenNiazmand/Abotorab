package ir.nilva.abotorab.helper

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.util.*

internal class CountryListDeserializer : JsonDeserializer<CountryList> {

    @Throws(JsonParseException::class)
    override fun deserialize(
        element: JsonElement,
        type: Type,
        context: JsonDeserializationContext
    ): CountryList {
        val jsonObject = element.asJsonObject
        val countries = ArrayList<CountryModel>()
        for ((_, value) in jsonObject.entrySet()) {
            val country = context.deserialize<CountryModel>(value, CountryModel::class.java)
            countries.add(country)
        }
        return CountryList(countries)
    }

}