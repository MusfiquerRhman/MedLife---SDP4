package com.example.medlife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.util.*

class cartEdit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_edit)

        val changequantity = findViewById<EditText>(R.id.changequantity)
        val changebutton = findViewById<Button>(R.id.changebutton)
        val changedelete = findViewById<Button>(R.id.changedelete)
        val CartID = intent.getIntExtra("CartID", -1)
        val db = UserDatabase(this, null)
        val cart = db.getCartOfUser(CartID)
        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)
        var hour = myCalender.get(Calendar.HOUR_OF_DAY)
        val minuets = myCalender.get(Calendar.MINUTE)
        var prefix = "AM"
        if(hour > 12){
            prefix = "PM"
            hour = hour - 12
        }
        changebutton.setOnClickListener{
            val date ="${hour}:${minuets} ${prefix} - ${day}/${month}/${year}"
            val quantity = changequantity.text.toString().toInt()
            val userCartModel = UserCartModel(CartID, cart[0].userId, cart[0].medicineID, quantity, date, "FALSE")
            db.updateCartProduct(userCartModel)
            val intent = Intent(this, home_page::class.java)
            startActivity(intent)
        }

        changedelete.setOnClickListener{
            val date ="${hour}:${minuets} ${prefix} - ${day}/${month}/${year}"
            val quantity = 0
            val userCartModel = UserCartModel(CartID, cart[0].userId, cart[0].medicineID, quantity, date, "FALSE")
            db.deleteCart(userCartModel)
            val intent = Intent(this, home_page::class.java)
            startActivity(intent)
        }
    }
}