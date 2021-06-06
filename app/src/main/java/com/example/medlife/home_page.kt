package com.example.medlife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class home_page : AppCompatActivity(){
    companion object {
        var u_id: Int = MainActivity.u_id
        var u_name:String = MainActivity.u_name
        var u_email:String = MainActivity.u_email
        var u_password:String = MainActivity.u_password
        var u_dateOfBirth:String = MainActivity.u_dateOfBirth
        var u_address:String = MainActivity.u_address
        var u_phoneNo:String = MainActivity.u_phoneNo
        var u_userType:String = MainActivity.u_userType
    }

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }

    private lateinit var fabAdmin:FloatingActionButton
    private lateinit var fadPlus:FloatingActionButton
    private lateinit var fabUser:FloatingActionButton

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val navController = findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)

        fabAdmin = findViewById(R.id.fabAdmin)
        fadPlus = findViewById(R.id.fadPlus)
        fabUser = findViewById(R.id.fabUser)

        val userType = MainActivity.u_userType

        if (userType == "ADMIN"){
            fabAdmin.visibility = View.VISIBLE
            fabAdmin.isClickable = true
        } else {
            fabAdmin.visibility = View.INVISIBLE
            fabAdmin.isClickable = false
        }

        fabAdmin.setOnClickListener {
            onAdminButtonClicked()
        }

        fadPlus.setOnClickListener{
            val intent = Intent(this, add_drug_activity::class.java)
            startActivity(intent)
        }

        fabUser.setOnClickListener{
            val intent = Intent(this, AdminCart::class.java)
            startActivity(intent)
        }

    }

    private fun onAdminButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked){
            fadPlus.startAnimation(fromBottom)
            fabUser.startAnimation(fromBottom)
            fabAdmin.startAnimation(rotateOpen)
        } else {
            fadPlus.startAnimation(toBottom)
            fabUser.startAnimation(toBottom)
            fabAdmin.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            fadPlus.visibility = View.VISIBLE
            fabUser.visibility = View.VISIBLE
        } else {
            fadPlus.visibility = View.INVISIBLE
            fabUser.visibility = View.INVISIBLE
        }
    }

    private fun setClickable(clicked: Boolean){
        if(clicked){
            fabUser.isClickable = false
            fadPlus.isClickable = false
        } else {
            fabUser.isClickable = true
            fadPlus.isClickable = true
        }
    }
}