package ir.nilva.abotorab.helper

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import ir.nilva.abotorab.ApplicationContext
import ir.nilva.abotorab.R
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


fun getCountryName(code: String): String {
    val inputStream = ApplicationContext.context.resources.openRawResource(R.raw.nationality)
    val jsonString = readTextFile(inputStream)
    val jsonObject = JSONObject(jsonString)
    for (key in jsonObject.keys()) {
        val country = jsonObject.get(key) as JSONObject
        if (country.get("alpha_3") == code) {
            return country.get("en_name") as String;
        }
    }
    return ""
}

fun getCountries() : List<CountryModel>{
    val inputStream = ApplicationContext.context.resources.openRawResource(R.raw.nationality)
    val jsonString = readTextFile(inputStream)
    return GsonBuilder().registerTypeAdapter(CountryList::class.java, CountryListDeserializer())
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create().fromJson(jsonString, CountryList::class.java).countries
}

fun readTextFile(inputStream: InputStream): String {
    val outputStream = ByteArrayOutputStream()

    val buf = ByteArray(1024)
    var len = inputStream.read(buf)
    try {
        while (len != -1) {
            outputStream.write(buf, 0, len)
            len = inputStream.read(buf)
        }
        outputStream.close()
        inputStream.close()
    } catch (e: IOException) {

    }

    return outputStream.toString()
}