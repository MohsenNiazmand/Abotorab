package ir.nilva.abotorab.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ir.nilva.abotorab.db.model.DeliveryEntity

@Dao
interface DeliveryDao {

    @Query("SELECT * FROM delivery ORDER BY id DESC")
    fun getAll(): LiveData<List<DeliveryEntity>>

    @Query("SELECT * FROM delivery")
    suspend fun getDeliveries(): List<DeliveryEntity>

    @Insert
    suspend fun insert(items: List<DeliveryEntity>)

    @Delete
    suspend fun delete(item: DeliveryEntity)

    @Query("DELETE FROM delivery WHERE hashId NOT IN (SELECT hashId FROM delivery ORDER BY id DESC LIMIT 10)")
    suspend fun deleteOther()

    @Transaction
    suspend fun insertAndDeleteOther(item: DeliveryEntity) {
        insert(listOf(item))
        deleteOther()
    }

    @Query("DELETE FROM delivery")
    suspend fun clear()

}