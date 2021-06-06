package com.example.medlife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdminCart : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_cart)
        val orderView = findViewById<LinearLayout>(R.id.orderView)
        val db = UserDatabase(this, null)
        val users:ArrayList<Int> = db.getOrderCustomers()
        var userModel:ArrayList<UserModel> = ArrayList()

        for(i in users){
            userModel.add(db.getUser(i)[0])
        }

        Log.d("NoOfid", userModel.size.toString())

        val orderAdapter = AdminOrderPageAdapter(this, userModel)
        val orderStatusView = findViewById<RecyclerView>(R.id.placedOrders)
        orderStatusView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        orderStatusView.adapter = orderAdapter
        db.close()
    }

}