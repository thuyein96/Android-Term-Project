package com.example.toway.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import androidx.recyclerview.widget.RecyclerView
import com.example.toway.activities.OrderAcceptActivity
import com.example.toway.databinding.OrderListItemBinding
import com.example.toway.viewModels.Order

class OrderListAdapter(private val orders: List<Order>) :
    RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder>() {

    class OrderListViewHolder(val view: OrderListItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val binding =
            OrderListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return OrderListViewHolder(binding)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        val order = orders[position]

        holder.view.tvCustomerName.text = order.customerName
//        holder.view.tvLat.text = order.lat.toString()
//        holder.view.tvLong.text = order.lng.toString()
        holder.view.tvOrderProblem.text = order.problem
//        holder.view.tvPlateNumber.text = order.plateNumber
//        holder.view.tvPhoneNumber.text = order.phoneNumber

        holder.view.btnAccept.setOnClickListener {
            val intent = Intent(holder.view.root.context, OrderAcceptActivity::class.java)
            intent.putExtra("customername", order.customerName)
            intent.putExtra("latitute", order.lat)
            intent.putExtra("longitude", order.lng)
            intent.putExtra("problem", order.problem)
            intent.putExtra("plateNumber", order.plateNumber)
            intent.putExtra("phoneNumber", order.phoneNumber)
            holder.view.root.context.startActivity(intent)
        }
    }
}