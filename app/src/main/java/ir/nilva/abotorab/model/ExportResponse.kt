package ir.nilva.abotorab.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ExportResponse(
    @SerializedName("url") val url: String
) : Serializable