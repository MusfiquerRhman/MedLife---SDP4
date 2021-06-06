package com.example.medlife

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdminOrderPageAdapter (private val context: Context, private val item: ArrayList<UserModel>)
    : RecyclerView.Adapter<AdminOrderPageAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val orderView: LinearLayout = view.findViewById(R.id.orderView)
        val cartOrderName: TextView = view.findViewById(R.id.cartOrderName)
        val cartOrderPhoneNO: TextView = view.findViewById(R.id.cartOrderPhoneNO)
        val cartOrderAddress: TextView = view.findViewById(R.id.cartOrderAddress)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: UserModel = item[position]
        holder.cartOrderName.text = model.name
        holder.cartOrderPhoneNO.text = model.phoneNo
        holder.cartOrderAddress.text = model.address

        holder.orderView.setOnClickListener{
            val intent = Intent(context, orderDetailsPage::class.java)
            intent.putExtra("PositionClicked", position)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.order_header, parent, false))
    }

    override fun getItemCount(): Int {
        return item.size
    }
}
