package ir.nilva.abotorab.view.page.operation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.nilva.abotorab.R
import ir.nilva.abotorab.db.model.DeliveryEntity
import ir.nilva.abotorab.helper.mapCabinetLabelWithCab
import kotlinx.android.synthetic.main.item_delivery.view.*

class RecentGivesAdapter(val listener: RecentGivesListener) :
    ListAdapter<DeliveryEntity, RecentGivesAdapter.RecentGiveVH>(DeliveryDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecentGiveVH(
        LayoutInflater.from(parent.context).inflate(R.layout.item_delivery, null)
    )

    override fun onBindViewHolder(holder: RecentGiveVH, position: Int) =
        holder.bind(getItem(position))

    inner class RecentGiveVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: DeliveryEntity) = with(itemView) {
            title.text = item.nickname
            phone.text = item.phone
            country.text = item.country
            cellCode.text = "شماره قفسه: ${mapCabinetLabelWithCab(item.cellCode)}"
            subTitle.text = item.exitedAt
            setOnClickListener { listener.undoClicked(item) }
        }
    }

    object DeliveryDiff : DiffUtil.ItemCallback<DeliveryEntity>() {
        override fun areItemsTheSame(oldItem: DeliveryEntity, newItem: DeliveryEntity) =
            oldItem.hashId == newItem.hashId

        override fun areContentsTheSame(oldItem: DeliveryEntity, newItem: DeliveryEntity) =
            areItemsTheSame(oldItem, newItem)
    }

}