package com.example.medlife

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import java.util.*
import kotlin.collections.ArrayList

class add_drug_activity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_drug_activity)

        val dropdownItems = findViewById<TextView>(R.id.dropdown_items)
        val items = listOf("Tablet", "Capsule", "Injection", "Drops", "Liquid", "Spray", "Gel", "Solution")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (dropdownItems as? AutoCompleteTextView)?.setAdapter(adapter)

        val med_add = findViewById<Button>(R.id.med_add)
        med_add.setOnClickListener(this)

        val expireDatePicker = findViewById<Button>(R.id.expireDatePicker)
        expireDatePicker.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.med_add -> {
                val medName = findViewById<EditText>(R.id.med_name).text.toString()
                val medPrice = findViewById<EditText>(R.id.med_price).text.toString()
                val medQuantity = findViewById<EditText>(R.id.med_quantity).text.toString()
                val medDescription = findViewById<EditText>(R.id.med_description).text.toString()
                val medCategory = findViewById<AutoCompleteTextView>(R.id.dropdown_items).text.toString()
                val expireDate = findViewById<EditText>(R.id.expiredate).text.toString()

                if (medName.isEmpty() || medPrice.isEmpty() || medQuantity.isEmpty() || medDescription.isEmpty() || medCategory.isEmpty() || expireDate.isEmpty()){
                    val intent: Intent = Intent(this, add_drug_activity::class.java)
                    val med_error = findViewById<TextView>(R.id.med_error)
                    med_error.text = "Please fill all the fields"
                    startActivity(intent)
                } else {
                    val db = UserDatabase(this, null)
                    val model = MedicineShopModel(
                            name = medName,
                            description = medDescription,
                            quantity = medQuantity.toInt(),
                            price = medPrice.toDouble(),
                            _id = 1,
                            category = medCategory,
                            expireDate = expireDate)

                    val success = db.addMedicine(model)
                    if(success == (-1).toLong()){
                        val intent: Intent = Intent(this, add_drug_activity::class.java)
                        val med_error = findViewById<TextView>(R.id.med_error)
                        med_error.text = "Faild to add Medicine, Try again"
                        startActivity(intent)
                    } else {
                        val shop = UserDatabase(this, null)
                        val shopList:ArrayList<MedicineShopModel> = shop.getMedicine()
                        Log.d("list", shopList.size.toString())
                        val intent: Intent = Intent(this, home_page::class.java)
                        startActivity(intent)
                    }
                }
            }

            R.id.expireDatePicker -> {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val expireDate = findViewById<EditText>(R.id.expiredate)

                val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                        view, mYear, mMonth, mDate ->
                    val selectedDate = "$mDate/${mMonth+1}/$mYear"
                    expireDate.setText(selectedDate) }, year, month, day)

                datePicker.datePicker.minDate = System.currentTimeMillis() + 1000*60*60*24;
                datePicker.show()
            }
        }
    }
}