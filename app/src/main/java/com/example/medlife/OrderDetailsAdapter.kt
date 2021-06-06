package com.example.medlife

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderDetailsAdapter (private val context: Context, private val item: ArrayList<UserCartModel>)
    : RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val cartMedName: TextView = view.findViewById(R.id.cartMedName)
        val cartQuantity: TextView = view.findViewById(R.id.cartQuantity)
        val cartPrice: TextView = view.findViewById(R.id.cartPrice)
        val totalInfo: TextView = view.findViewById(R.id.totalInfo)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: UserCartModel = item[position]
        val db = UserDatabase(context, null)
        val med = db.getMedicine(model.medicineID)
        holder.cartMedName.text = med[0].name
        holder.cartQuantity.text = model.quantity.toString()
        holder.cartPrice.text = (med[0].price * model.quantity).toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.order_header, parent, false))
    }

    override fun getItemCount(): Int {
        return item.size
    }
}
