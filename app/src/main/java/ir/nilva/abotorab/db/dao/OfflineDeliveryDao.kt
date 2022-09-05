package ir.nilva.abotorab.db.dao

import androidx.room.*
import ir.nilva.abotorab.db.model.OfflineDeliveryEntity

@Dao
interface OfflineDeliveryDao {

    @Query("SELECT * FROM offline_delivery")
    suspend fun getAll(): List<OfflineDeliveryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: OfflineDeliveryEntity)

    @Query("DELETE FROM offline_delivery WHERE hashId =:hashId")
    suspend fun delete(hashId: String)

    @Delete
    suspend fun delete(item: OfflineDeliveryEntity)

    @Query("DELETE FROM offline_delivery")
    suspend fun clear()

}