package com.example.medlife

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShopRecycleAdapter( private val context: Context, private val item: ArrayList<MedicineShopModel>, private val listener: OnItemClickListener)
    : RecyclerView.Adapter<ShopRecycleAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        val tvItem: LinearLayout = view.findViewById(R.id.shop_view)
        val shopMedicineName: TextView = view.findViewById(R.id.shop_medicine_name)
        val shopPriceView: TextView = view.findViewById(R.id.shop_price_view)

        init {
            shopMedicineName.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.shop_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: MedicineShopModel = item[position]
        holder.shopMedicineName.text = model.name
        val price = model.price.toString() + " TK"
        holder.shopPriceView.text = price
    }

    override fun getItemCount(): Int {
        return item.size
    }
}