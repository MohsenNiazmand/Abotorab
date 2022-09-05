package ir.nilva.abotorab.model

import com.google.gson.annotations.SerializedName

data class StartReportResponse(
    @SerializedName("total_cabinets") val totalCabinets: Int,
    @SerializedName("total_cells") val totalCells: Int,
    @SerializedName("empty_cells") val emptyCells: Int,
    @SerializedName("total_deliveries") val totalDeliveries: Int
)