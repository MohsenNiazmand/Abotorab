package ir.nilva.abotorab.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ir.nilva.abotorab.webservices.BaseResponse
import java.io.Serializable

@Entity
class CabinetResponse(
    @PrimaryKey
    @SerializedName("code") val code: String,
    @SerializedName("rows") val rows: ArrayList<Row>
    ) : BaseResponse(), Serializable