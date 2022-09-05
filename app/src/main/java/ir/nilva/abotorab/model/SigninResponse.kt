package ir.nilva.abotorab.model

import com.google.gson.annotations.SerializedName
import ir.nilva.abotorab.webservices.BaseResponse

class SigninResponse(@SerializedName("token") val token: String, @SerializedName("depository") val depository: String) :
    BaseResponse()