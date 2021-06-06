package com.example.medlife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
         var u_id: Int = 0
         lateinit var u_name:String
         lateinit var u_email:String
         lateinit var u_password:String
         lateinit var u_dateOfBirth:String
         lateinit var u_address:String
         lateinit var u_phoneNo:String
         lateinit var u_userType:String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val login_button = findViewById<Button>(R.id.login_button)
        login_button.setOnClickListener(this)

        val login_register_button = findViewById<Button>(R.id.login_register_button)
        login_register_button.setOnClickListener(this)

//        val db = UserDatabase(this, null)
//        val status = db.getStatus()
//
//        if(status.isNotEmpty()){
//            if (status[0]._id == 1){
//                val intent = Intent(this, home_page::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.login_register_button ->{
                val intent = Intent(this, register_activity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.login_button -> {
                val email: String = findViewById<EditText>(R.id.edit_loginUserEmail).text.toString()
                val password: String = findViewById<EditText>(R.id.edit_loginPassword).text.toString()
                val db = UserDatabase(this, null)
                val userList: ArrayList<UserModel> = db.getUser(email, password)
                db.close()
                if(userList.isEmpty()){
                    val textViewLoginWarning = findViewById<TextView>(R.id.textViewLoginWarning)
                    Toast.makeText(this, "Wrong username/password", Toast.LENGTH_LONG).show()
                    textViewLoginWarning.text = "Wrong username/password"
                }
                else {
                    u_id = userList[0].id
                    u_name = userList[0].name
                    u_email = userList[0].email
                    u_address = userList[0].address
                    u_dateOfBirth = userList[0].dateOfBirth
                    u_phoneNo = userList[0].phoneNo
                    u_password = userList[0].password
                    u_userType = userList[0].userType

                    //db.setStatus(userList[0].id, 1)

                    val intent = Intent(this, home_page::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
