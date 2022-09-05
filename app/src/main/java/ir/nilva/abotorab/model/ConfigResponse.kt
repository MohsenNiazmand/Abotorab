package ir.nilva.abotorab.model

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ir.nilva.abotorab.webservices.BaseResponse

class ConfigResponse (
    @Expose @SerializedName("token") val token : String,
    @Expose @SerializedName("row_code_mapping") val row_code_mapping : JsonObject
) : BaseResponse()