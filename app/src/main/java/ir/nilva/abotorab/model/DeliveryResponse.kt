package ir.nilva.abotorab.model

import com.google.gson.annotations.SerializedName

data class DeliveryResponse(
    @SerializedName("pilgrim") val pilgrim: Pilgrim,
    @SerializedName("taker") val taker: Int,
    @SerializedName("giver") val giver: Int,
    @SerializedName("hash_id") val hashId: String,
    @SerializedName("entered_at") val enteredAt: String,
    @SerializedName("exited_at") val exitAt: String,
    @SerializedName("exit_type") val exitType: Int,
    @SerializedName("pack") val pack: ArrayList<Pack>
)

data class Pilgrim(
    @SerializedName("phone") val phone: String,
    @SerializedName("country") val country: String,
    @SerializedName("name") val name: String
)

data class Pack(
    @SerializedName("id") val id: Int,
    @SerializedName("bag_count") val bagCount: Int,
    @SerializedName("suitcase_count") val suitcaseCount: Int,
    @SerializedName("pram_count") val pramCount: Int,
    @SerializedName("delivery") val delivery: Int,
    @SerializedName("cell") val cell: String
)


data class Failure(
    @SerializedName("non_field_errors") val errors: List<String>
)
