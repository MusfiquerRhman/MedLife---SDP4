package com.example.medlife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class EditMedicine : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_medicine)

        val dropdownItems = findViewById<TextView>(R.id.edit_dropdown_items)
        val items = listOf("Tablet", "Capsule", "Injection", "Drops", "Liquid", "Spray", "Gel", "Solution")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (dropdownItems as? AutoCompleteTextView)?.setAdapter(adapter)

        val medName = findViewById<EditText>(R.id.edit_med_name)
        val medPrice = findViewById<EditText>(R.id.edit_med_price)
        val medQuantity = findViewById<EditText>(R.id.edit_med_quantity)
        val medDescription = findViewById<EditText>(R.id.edit_med_description)
        val medCategory = findViewById<AutoCompleteTextView>(R.id.edit_dropdown_items)
        val expireDate = findViewById<EditText>(R.id.edit_med_expiredate)

        val db = UserDatabase(this, null)
        val medid = intent.getIntExtra("MedID", -1)
        val medicine = db.getMedicine(medid)[0]

        medName.setText(medicine.name)
        medPrice.setText(medicine.price.toString())
        medQuantity.setText(medicine.quantity.toString())
        medDescription.setText(medicine.description)
        medCategory.setText(medicine.category)
        expireDate.setText(medicine.expireDate)

        val doneButton = findViewById<Button>(R.id.edit_med_add)
        doneButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.edit_med_add -> {
                val medName = findViewById<EditText>(R.id.edit_med_name)
                val medPrice = findViewById<EditText>(R.id.edit_med_price)
                val medQuantity = findViewById<EditText>(R.id.edit_med_quantity)
                val medDescription = findViewById<EditText>(R.id.edit_med_description)
                val medCategory = findViewById<AutoCompleteTextView>(R.id.edit_dropdown_items)
                val expireDate = findViewById<EditText>(R.id.edit_med_expiredate)
                val db = UserDatabase(this, null)

                if (medName.text.isEmpty() || medPrice.text.isEmpty() || medQuantity.text.isEmpty() || medDescription.text.isEmpty()
                        || medCategory.text.isEmpty() || expireDate.text.isEmpty()){
                    val intent: Intent = Intent(this, EditMedicine::class.java)
                    val med_error = findViewById<TextView>(R.id.edit_med_error)
                    med_error.text = "Please fill all the fields"
                    startActivity(intent)
                } else {
                    val model = MedicineShopModel(
                        name = medName.text.toString(),
                        description = medDescription.text.toString(),
                        quantity = medQuantity.text.toString().toInt(),
                        price = medPrice.text.toString().toDouble(),
                        _id = intent.getIntExtra("MedID",1),
                        category = medCategory.text.toString(),
                        expireDate = expireDate.text.toString())

                    db.updateShopProduct(model)
                    startActivity(Intent(this, home_page::class.java))
                    finish()
                }
            }
        }
    }
}