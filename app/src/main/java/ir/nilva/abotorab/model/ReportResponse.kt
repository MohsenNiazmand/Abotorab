package ir.nilva.abotorab.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ir.nilva.abotorab.webservices.BaseResponse

class ReportResponse(@Expose @SerializedName("in_house") val house: House,
                     @Expose @SerializedName("distribution") val distribution: Distribution
): BaseResponse()

class House(@Expose @SerializedName("3") val in3Hour: Int,
            @Expose @SerializedName("6") val in6Hour: Int,
            @Expose @SerializedName("24") val in24Hour: Int,
            @Expose @SerializedName("48") val in48Hour: Int)

class Distribution(@Expose @SerializedName("total") val total: Int,
                   @Expose @SerializedName("0") val deliveredToCustomer: Int,
                   @Expose @SerializedName("1") val deliveredToStore: Int,
                   @Expose @SerializedName("2") val missed: Int)