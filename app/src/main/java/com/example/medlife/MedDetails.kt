package com.example.medlife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MedDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_med_details)

        val detailsName = findViewById<TextView>(R.id.details_name)
        val detailsPrice = findViewById<TextView>(R.id.details_price)
        val detailsCategory = findViewById<TextView>(R.id.details_category)
        val detailsAvailable = findViewById<TextView>(R.id.details_available)
        val detailsDescription = findViewById<TextView>(R.id.details_description)
        val detailsQuantity = findViewById<EditText>(R.id.details_quantity)
        val detailsExpiredate = findViewById<TextView>(R.id.details_expireDate)

        val db = UserDatabase(this, null)
        val medId = intent.getIntExtra("MedID", -1)
        val med = db.getMedicine(medId)

        detailsName.text = med[0].name
        detailsPrice.text = med[0].price.toString()
        detailsCategory.text = med[0].category
        detailsDescription.text = med[0].description
        detailsAvailable.text = "${med[0].quantity} items left"
        detailsExpiredate.text = med[0].expireDate

        val editMedicine = findViewById<Button>(R.id.editMedicine)
        if(MainActivity.u_userType != "ADMIN"){
            editMedicine.visibility = View.INVISIBLE
            editMedicine.isClickable = false
        } else {
            editMedicine.visibility = View.VISIBLE
            editMedicine.isClickable = true
        }

        findViewById<Button>(R.id.addToCart).setOnClickListener{
            if(medId == -1){
                Toast.makeText(this, "Medicine not found", Toast.LENGTH_LONG).show()
            } else {
                if(detailsQuantity.text.isEmpty()){
                    Toast.makeText(this, "Enter Quantity", Toast.LENGTH_LONG).show()
                } else {
                    val available = db.getMedicine(medId)[0].quantity
                    if(available < detailsQuantity.text.toString().toInt()){
                        Toast.makeText(this, "Not Enough in Stock", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        db.addToCart(MainActivity.u_id, medId, detailsQuantity.text.toString().toInt())
                        db.reduceMedFromStock(medId, detailsQuantity.text.toString().toInt(), med[0].quantity)
                        startActivity(Intent(this, home_page::class.java))
                        finish()
                    }
                }
            }
        }



        editMedicine.setOnClickListener{
            val intent = Intent(this, EditMedicine::class.java)
            intent.putExtra("MedID", medId)
            startActivity(intent)
        }

    }
}