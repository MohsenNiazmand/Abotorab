package ir.nilva.abotorab.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.nilva.abotorab.ApplicationContext
import ir.nilva.abotorab.db.dao.CabinetDao
import ir.nilva.abotorab.db.dao.DeliveryDao
import ir.nilva.abotorab.db.dao.OfflineDeliveryDao
import ir.nilva.abotorab.db.model.DeliveryEntity
import ir.nilva.abotorab.db.model.OfflineDeliveryEntity
import ir.nilva.abotorab.model.CabinetResponse

/**
 * The Room database for this app
 */
@Database(entities = [DeliveryEntity::class, CabinetResponse::class, OfflineDeliveryEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deliveryDao(): DeliveryDao
    abstract fun cabinetDao(): CabinetDao
    abstract fun offlineDeliveryDao(): OfflineDeliveryDao

    companion object {

        @Volatile private var instance: AppDatabase? = null

        fun getInstance(): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(ApplicationContext.context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "app")
//                .addCallback(object : RoomDatabase.Callback() {
//                    override fun onCreate(db: SupportSQLiteDatabase) {
//                        super.onCreate(db)
//                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
//                        WorkManager.getInstance(context).enqueue(request)
//                    }
//                })
                .build()
        }
    }
}