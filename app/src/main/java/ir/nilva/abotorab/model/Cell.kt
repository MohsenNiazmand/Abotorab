package ir.nilva.abotorab.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Cell(
    @SerializedName("id") val id: Int,
    @SerializedName("code") val code: String,
    @SerializedName("age") var age: Int,
    @SerializedName("pilgrim") var pilgrim: Pilgrim?,
    @SerializedName("pack") var pack: Pack?,
    @SerializedName("is_healthy") var isHealthy: Boolean,
    @SerializedName("is_fav") var isFavorite: Boolean,
    @SerializedName("size") val size: Int,
    @SerializedName("row") val row: Int
) : Serializable