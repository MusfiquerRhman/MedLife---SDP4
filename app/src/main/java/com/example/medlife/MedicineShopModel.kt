package com.example.medlife

data class MedicineShopModel(
                        val _id: Int,
                        val name:String,
                        val price:Double,
                        val category:String,
                        val quantity:Int,
                        val expireDate: String,
                        val description: String)