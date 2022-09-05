package ir.nilva.abotorab.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.nilva.abotorab.model.Row


class Converters {
    @TypeConverter
    fun fromString(value: String) =
        Gson().fromJson<ArrayList<Row>>(value, object : TypeToken<ArrayList<Row>>() {}.type)

    @TypeConverter
    fun fromRows(list: ArrayList<Row>) = Gson().toJson(list)
}