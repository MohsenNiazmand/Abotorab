package ir.nilva.abotorab.view.page.operation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import ir.nilva.abotorab.R
import ir.nilva.abotorab.db.AppDatabase
import ir.nilva.abotorab.db.model.DeliveryEntity
import ir.nilva.abotorab.helper.toastSuccess
import ir.nilva.abotorab.view.page.base.BaseActivity
import ir.nilva.abotorab.view.widget.MarginItemDecoration
import ir.nilva.abotorab.webservices.callWebservice
import ir.nilva.abotorab.webservices.getServices
import kotlinx.android.synthetic.main.activity_recent_gives.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecentGivesActivity : BaseActivity(),
    RecentGivesListener {

    private lateinit var adapter: RecentGivesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_gives)

        adapter = RecentGivesAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(MarginItemDecoration(20))

        AppDatabase.getInstance().deliveryDao()
            .getAll().observe(this, Observer {
                if (it.isNullOrEmpty()) {
                    emptyState.visibility = View.VISIBLE
                } else {
                    emptyState.visibility = View.GONE
                }
                adapter.submitList(it)
            })
    }

    override fun undoClicked(item: DeliveryEntity) {
        CoroutineScope(Dispatchers.Main).launch {
            callWebservice { getServices().undoDelivery(item.hashId) }?.run {
                toastSuccess("این محموله بازگردانده شد")
                AppDatabase.getInstance().deliveryDao().delete(item)
            }
        }
    }

}

interface RecentGivesListener {
    fun undoClicked(item: DeliveryEntity)
}

