package com.example.medlife

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import java.util.*

class register_activity : AppCompatActivity(), View.OnClickListener {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_activity)

        val register: Button = findViewById<Button>(R.id.register_button)
        register.setOnClickListener(this)

        val registerLoginButton = findViewById<Button>(R.id.register_loginButton)
        registerLoginButton.setOnClickListener(this)

        val dateOfBirthPicker = findViewById<Button>(R.id.dateOfBirthPicker)
        dateOfBirthPicker.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.register_button -> {
                val username = findViewById<EditText>(R.id.edit_username).text.toString()
                val email = findViewById<EditText>(R.id.edit_userEmail).text.toString()
                val password = findViewById<EditText>(R.id.edit_password).text.toString()
                val retypePassword = findViewById<EditText>(R.id.edit_retypePassword).text.toString()
                val phone = findViewById<EditText>(R.id.edit_phoneNo).text.toString()
                val address = findViewById<EditText>(R.id.edit_address).text.toString()
                val dateOfBirth = findViewById<EditText>(R.id.edit_dateOfBirth).text.toString()

                if(password != retypePassword){
                    val wrongPassword = findViewById<TextView>(R.id.wrongPassword)
                    wrongPassword.text = "Password not matched"
                } else {
                    val userInfo = UserModel(
                            name = username,
                            email = email,
                            password = password,
                            phoneNo = phone,
                            address = address,
                            dateOfBirth = dateOfBirth,
                            id = 1,
                            userType = "User")
                    val db = UserDatabase(this, null)
                    db.addUser(userInfo)
                    db.close()
                    Toast.makeText(this, "Now login with your account", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            R.id.dateOfBirthPicker -> {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val editDateOfBirth = findViewById<EditText>(R.id.edit_dateOfBirth)

                val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                    view, mYear, mMonth, mDate ->
                    val selectedDate = "$mDate/${mMonth+1}/$mYear"
                    editDateOfBirth.setText(selectedDate) }, year, month, day)
                datePicker.datePicker.maxDate = System.currentTimeMillis();
                datePicker.show()
            }

            R.id.register_loginButton -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}