package ir.nilva.abotorab.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offline_delivery")
data class OfflineDeliveryEntity(
    @PrimaryKey val hashId: String
)