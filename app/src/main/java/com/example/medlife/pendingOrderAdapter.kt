package com.example.medlife

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class pendingOrderAdapter (private val context: Context, private val item: ArrayList<UserCartModel>)
    : RecyclerView.Adapter<pendingOrderAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val cartMedName: TextView = view.findViewById(R.id.cartMedName)
        val cartQuantity: TextView = view.findViewById(R.id.cartQuantity)
        val cartPrice: TextView = view.findViewById(R.id.cartPrice)

    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: UserCartModel = item[position]
        val db = UserDatabase(context, null)
        val data = db.getMedicine(model.medicineID)
        holder.cartMedName.text = data[0].name
        holder.cartQuantity.text = model.quantity.toString()
        val price = (data[0].price * model.quantity).toString() + "TK"
        holder.cartPrice.text = price
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.cart_item, parent, false))
    }
}