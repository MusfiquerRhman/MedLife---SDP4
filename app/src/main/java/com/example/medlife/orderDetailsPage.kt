package com.example.medlife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class orderDetailsPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details_page)

        val totalInfo: TextView = findViewById(R.id.totalInfo)
        val db = UserDatabase(this, null)
        val PositionClicked = intent.getIntExtra("PositionClicked",-1)
        val user = db.getCartAll()[PositionClicked].userId
        val cart = db.getCartAll(user)

        val orderAdapter = OrderDetailsAdapter(this, cart)
        val orderStatusView = findViewById<RecyclerView>(R.id.userOrderDetails)
        orderStatusView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        orderStatusView.adapter = orderAdapter
        db.close()
    }
}