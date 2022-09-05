package ir.nilva.abotorab.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Row(
    @SerializedName("id") val id: Int,
    @SerializedName("cells") val cells: ArrayList<Cell>,
    @SerializedName("code") val code:  Int,
    @SerializedName("cabinet") val cabinet: Int
) : Serializable