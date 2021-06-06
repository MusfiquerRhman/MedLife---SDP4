package com.example.medlife

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class userPendingOrders : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_pending_orders)

        val db = UserDatabase(this, null)
        val list: ArrayList<UserCartModel> = db.getCartAllTrue()

        //val data: ArrayList<MedicineShopModel> = db.getMedicine()
        val cartAdapter = pendingOrderAdapter(this, list)
        val cartStatusView = findViewById<RecyclerView>(R.id.pendingOrderItems)
        val cartInfo = findViewById<TextView>(R.id.orderInfo)
        var totalMed = 0
        var totalPrice = 0.0
        for(item in list){
            totalMed += item.quantity
            totalPrice += db.getMedicine(item.medicineID)[0].price * item.quantity
        }

        if (cartInfo != null) {
            cartInfo.text = "${totalMed} items: ${totalPrice} TK"
        }

        if (cartStatusView != null) {
            cartStatusView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            cartStatusView.adapter = cartAdapter
        }

        db.close()
    }
}