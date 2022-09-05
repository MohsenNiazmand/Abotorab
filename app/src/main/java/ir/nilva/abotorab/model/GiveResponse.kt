package ir.nilva.abotorab.model

import com.google.gson.annotations.SerializedName
import ir.nilva.abotorab.webservices.BaseResponse

class GiveResponse (
    @SerializedName("pilgrim") val pilgrim: Pilgrim,
    @SerializedName("hash_id") val hashId: String,
    @SerializedName("exited_at") val exitAt: String,
    @SerializedName("cell_code") val cellCode: String
): BaseResponse()