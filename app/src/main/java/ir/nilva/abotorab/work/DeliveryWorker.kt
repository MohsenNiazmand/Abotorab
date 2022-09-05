package ir.nilva.abotorab.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ir.nilva.abotorab.db.AppDatabase
import ir.nilva.abotorab.webservices.callWebserviceWithFailure
import ir.nilva.abotorab.webservices.getServices
import kotlinx.coroutines.coroutineScope

class DeliveryWorker(
    val context: Context,
    workParams: WorkerParameters
) : CoroutineWorker(context, workParams) {

    override suspend fun doWork(): Result = coroutineScope {
        val offlineDeliveries = AppDatabase.getInstance().offlineDeliveryDao().getAll()
        if (offlineDeliveries.isEmpty()) {
            Result.success()
        } else {
            context.callWebserviceWithFailure({ getServices().give(offlineDeliveries.map { it.hashId }) }) { response, code ->
                Result.retry()
            }?.run {
                this.forEach {
                    AppDatabase.getInstance().offlineDeliveryDao().delete(it.hashId)
                }
            }
            Result.success()
        }
    }
}