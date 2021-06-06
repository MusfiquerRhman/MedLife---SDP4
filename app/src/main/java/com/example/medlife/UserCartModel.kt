package com.example.medlife

import java.util.*

data class UserCartModel (val _id:Int,
                          val userId: Int,
                          val medicineID: Int,
                          val quantity: Int,
                          var isConfirmed: String,
                          val date:String)