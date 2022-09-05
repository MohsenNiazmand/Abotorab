package ir.nilva.abotorab.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "delivery")
data class DeliveryEntity(
    val nickname: String,
    val country: String,
    val cellCode: String,
    val phone: String,
    val hashId: String,
    val exitedAt: String
){
    @PrimaryKey(autoGenerate = true) var id: Long? = null

}