package com.example.medlife

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

class UserDatabase(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){
    companion object {
        private const val DATABASE_VERSION = 11
        private const val DATABASE_NAME = "MedLife"

        // table names
        private const val USER_TABLE = "User_info"
        private const val SHOP_TABLE = "ShopTable"
        private const val CART_TABLE = "CartTable"
        private const val ORDER_TABLE = "OrderTable"
        //private const val STATUS_TABLE = "StatusTable"

        //user table
        private const val U_ID = "_id"
        private const val U_NAME = "username"
        private const val U_EMAIL = "user_email"
        private const val U_PASSWORD = "password"
        private const val U_DATE_OF_BIRTH = "date_of_birth"
        private const val U_ADDRESS = "address"
        private const val U_PHONE_NO = "phone_no"
        private const val U_USER_TYPE = "user_type"

        //shop table
        private const val S_PRODUCT_ID = "_id"
        private const val S_PRODUCT_NAME = "name"
        private const val S_PRODUCT_PRICE = "price"
        private const val S_PRODUCT_CATEGORY = "category"
        private const val S_PRODUCT_QUANTITY = "quantity"
        private const val S_PRODUCT_DESCRIPTION = "description"
        private const val S_PRODUCT_EXPIREDATE = "expireDate"

        // cart table
        private const val CART_ID = "_id"
        private const val CART_USER_ID = "userId"
        private const val CART_MEDICINE_ID = "medicineID"
        private const val CART_QUANTITY = "quantity"
        private const val CART_DATE = "date"
        private const val CART_CONFIRMED = "isConfirmed"

        // user status
        //private const val st_id = "_id"
        //private const val st_userId = "userId"
        //private const val st_isLoggedIn = "isLoggedIn"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createShopTable = ("CREATE TABLE " + SHOP_TABLE + " ( " + S_PRODUCT_ID + " INTEGER PRIMARY KEY, " + S_PRODUCT_NAME + " TEXT, " + S_PRODUCT_PRICE + " REAL," +
                S_PRODUCT_EXPIREDATE + " TEXT, " + S_PRODUCT_CATEGORY + " TEXT, " + S_PRODUCT_QUANTITY + " INTEGER, " + S_PRODUCT_DESCRIPTION + " TEXT)")

        val createUserTable = ("CREATE TABLE " + USER_TABLE + " (" + U_ID + " INTEGER PRIMARY KEY, " + U_NAME + " TEXT," + U_EMAIL + " TEXT," +
                U_PASSWORD + " TEXT," + U_DATE_OF_BIRTH + " TEXT," + U_ADDRESS + " TEXT," + U_USER_TYPE + " TEXT," + U_PHONE_NO + " TEXT)")

        val createCartTable = ("CREATE TABLE " + CART_TABLE + " (" + CART_ID + " INTEGER PRIMARY KEY, " + CART_USER_ID + " INTEGER," + CART_MEDICINE_ID + " INTEGER," +
                CART_CONFIRMED + " TEXT," + CART_QUANTITY + " INTEGER," + CART_DATE + " TEXT)")

        //val createStatusTable = ("CREATE TABLE $STATUS_TABLE ($st_id INTEGER PRIMARY KEY, $st_userId TEXT, $st_isLoggedIn INTEGER)")

        db?.execSQL(createShopTable)
        db?.execSQL(createUserTable)
        db?.execSQL(createCartTable)
        //db?.execSQL(createStatusTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $USER_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $SHOP_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $CART_TABLE")
        onCreate(db)
    }

    /*
    ================================================================================================
    user functions
    ================================================================================================
     */

    fun addUser(user: UserModel):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(U_NAME, user.name)
        contentValues.put(U_EMAIL, user.email)
        contentValues.put(U_PASSWORD, user.password)
        contentValues.put(U_DATE_OF_BIRTH, user.dateOfBirth)
        contentValues.put(U_ADDRESS, user.address)
        contentValues.put(U_PHONE_NO, user.phoneNo)
        contentValues.put(U_USER_TYPE, user.userType)
        val success = db.insert(USER_TABLE, null, contentValues)
        db.close()
        return success
    }

    fun getUser(enteredEmail: String, enteredPassword:String): ArrayList<UserModel> {
        val userList: ArrayList<UserModel> = ArrayList()
        val query = "SELECT * FROM $USER_TABLE WHERE $U_EMAIL = '$enteredEmail' AND $U_PASSWORD = '$enteredPassword'"
        val db = this.readableDatabase
        var cursor: Cursor?

        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }
        var id:Int
        var name:String
        var email:String
        var password:String
        var dateOfBirth:String
        var address:String
        var phoneNo:String
        var usertype:String

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex(U_ID))
                name = cursor.getString(cursor.getColumnIndex(U_NAME))
                email = cursor.getString(cursor.getColumnIndex(U_EMAIL))
                password = cursor.getString(cursor.getColumnIndex(U_PASSWORD))
                dateOfBirth = cursor.getString(cursor.getColumnIndex(U_DATE_OF_BIRTH))
                address = cursor.getString(cursor.getColumnIndex(U_ADDRESS))
                phoneNo = cursor.getString(cursor.getColumnIndex(U_PHONE_NO))
                usertype = cursor.getString(cursor.getColumnIndex(U_USER_TYPE))

                val emp = UserModel(id = id,
                        name = name,
                        email = email,
                        password = password,
                        dateOfBirth = dateOfBirth,
                        address = address,
                        phoneNo = phoneNo,
                        userType = usertype)
                userList.add(emp)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }

    fun getUser(userId: Int): ArrayList<UserModel> {
        val userList: ArrayList<UserModel> = ArrayList()
        val query = "SELECT * FROM $USER_TABLE WHERE $U_ID = $userId"
        val db = this.readableDatabase
        var cursor: Cursor?

        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }
        var id:Int
        var name:String
        var email:String
        var password:String
        var dateOfBirth:String
        var address:String
        var phoneNo:String
        var usertype:String

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex(U_ID))
                name = cursor.getString(cursor.getColumnIndex(U_NAME))
                email = cursor.getString(cursor.getColumnIndex(U_EMAIL))
                password = cursor.getString(cursor.getColumnIndex(U_PASSWORD))
                dateOfBirth = cursor.getString(cursor.getColumnIndex(U_DATE_OF_BIRTH))
                address = cursor.getString(cursor.getColumnIndex(U_ADDRESS))
                phoneNo = cursor.getString(cursor.getColumnIndex(U_PHONE_NO))
                usertype = cursor.getString(cursor.getColumnIndex(U_USER_TYPE))

                val emp = UserModel(id = id, name = name, email = email, password = password, dateOfBirth = dateOfBirth, address = address, phoneNo = phoneNo, userType = usertype)
                userList.add(emp)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }

    fun updateUser(user: UserModel): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(U_NAME, user.name)
        contentValues.put(U_EMAIL, user.email)
        contentValues.put(U_PASSWORD, user.password)
        contentValues.put(U_DATE_OF_BIRTH, user.dateOfBirth)
        contentValues.put(U_ADDRESS, user.address)
        contentValues.put(U_PHONE_NO, user.phoneNo)
        contentValues.put(U_USER_TYPE, user.userType)
        val success = db.update(USER_TABLE, contentValues, U_ID + "=" + user.id, null)
        db.close()
        return success
    }

    fun deleteUser(user: UserModel):Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(U_ID, user.id)
        val success = db.delete(USER_TABLE, U_ID + "=" + user.id, null)
        db.close()
        return success
    }

    /*
    ================================================================================================
    shop products functions
    ================================================================================================
     */

    fun addMedicine(medicineShopModel: MedicineShopModel): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(S_PRODUCT_NAME, medicineShopModel.name)
        contentValues.put(S_PRODUCT_PRICE, medicineShopModel.price)
        contentValues.put(S_PRODUCT_CATEGORY, medicineShopModel.category)
        contentValues.put(S_PRODUCT_DESCRIPTION, medicineShopModel.description)
        contentValues.put(S_PRODUCT_QUANTITY, medicineShopModel.quantity)
        contentValues.put(S_PRODUCT_EXPIREDATE, medicineShopModel.expireDate)
        val success =  db.insert(SHOP_TABLE, null, contentValues)
        db.close()
        return success
    }

    fun getMedicine(): ArrayList<MedicineShopModel>{
        val medicineList:ArrayList<MedicineShopModel> = ArrayList()
        val query = "SELECT * FROM $SHOP_TABLE"
        val db = this.readableDatabase
        var cursor: Cursor?

        try{
            cursor = db.rawQuery(query, null)
        } catch (e: SQLiteException) {
            Log.d("err", e.toString())
            return ArrayList()
        }

        var _id: Int
        var name:String
        var price:Double
        var category:String
        var quantity:Int
        var description: String
        var expireDate: String

        if (cursor != null) {
            if(cursor.moveToFirst()){
                do {
                    _id = cursor.getInt(cursor.getColumnIndex(S_PRODUCT_ID))
                    name = cursor.getString(cursor.getColumnIndex(S_PRODUCT_NAME))
                    price = cursor.getDouble(cursor.getColumnIndex(S_PRODUCT_PRICE))
                    category = cursor.getString(cursor.getColumnIndex(S_PRODUCT_CATEGORY))
                    description = cursor.getString(cursor.getColumnIndex(S_PRODUCT_DESCRIPTION))
                    quantity = cursor.getInt(cursor.getColumnIndex(S_PRODUCT_QUANTITY))
                    expireDate = cursor.getString(cursor.getColumnIndex(S_PRODUCT_EXPIREDATE))

                    val medicine = MedicineShopModel(_id = _id, name = name, price = price, category = category, quantity = quantity, description = description, expireDate = expireDate)
                    medicineList.add(medicine)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return medicineList
    }

    fun getMedicine(id: Int): ArrayList<MedicineShopModel>{
        val medicineList:ArrayList<MedicineShopModel> = ArrayList()
        val query = "SELECT * FROM $SHOP_TABLE WHERE $S_PRODUCT_ID = $id"
        val db = this.readableDatabase
        var cursor: Cursor?

        try{
            cursor = db.rawQuery(query, null)
        } catch (e: SQLiteException) {
            Log.d("err", e.toString())
            return ArrayList()
        }

        var _id: Int
        var name:String
        var price:Double
        var category:String
        var quantity:Int
        var description: String
        var expireDate: String

        if (cursor != null) {
            if(cursor.moveToFirst()){
                do {
                    _id = cursor.getInt(cursor.getColumnIndex(S_PRODUCT_ID))
                    name = cursor.getString(cursor.getColumnIndex(S_PRODUCT_NAME))
                    price = cursor.getDouble(cursor.getColumnIndex(S_PRODUCT_PRICE))
                    category = cursor.getString(cursor.getColumnIndex(S_PRODUCT_CATEGORY))
                    description = cursor.getString(cursor.getColumnIndex(S_PRODUCT_DESCRIPTION))
                    quantity = cursor.getInt(cursor.getColumnIndex(S_PRODUCT_QUANTITY))
                    expireDate = cursor.getString(cursor.getColumnIndex(S_PRODUCT_EXPIREDATE))

                    val medicine = MedicineShopModel(_id = _id, name = name, price = price, category = category, quantity = quantity, description = description, expireDate = expireDate)
                    medicineList.add(medicine)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return medicineList
    }

    fun updateShopProduct(medicine: MedicineShopModel): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(S_PRODUCT_NAME, medicine.name)
        contentValues.put(S_PRODUCT_PRICE, medicine.price)
        contentValues.put(S_PRODUCT_CATEGORY, medicine.category)
        contentValues.put(S_PRODUCT_QUANTITY, medicine.quantity)
        contentValues.put(S_PRODUCT_DESCRIPTION, medicine.description)
        contentValues.put(S_PRODUCT_EXPIREDATE, medicine.expireDate)
        val success = db.update(SHOP_TABLE, contentValues, S_PRODUCT_ID + "=" + medicine._id, null)
        db.close()
        return success
    }

    fun reduceMedFromStock(id: Int, ordered: Int, inStock:Int):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(S_PRODUCT_QUANTITY, inStock-ordered)
        val success = db.update(SHOP_TABLE, contentValues, S_PRODUCT_ID + "=" + id, null)
        db.close()
        return success
    }

    fun deleteShopProduct(medicine: MedicineShopModel):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(S_PRODUCT_ID, medicine._id)
        val success = db.delete(SHOP_TABLE, S_PRODUCT_ID + "=" + medicine._id, null)
        db.close()
        return success
    }

    /*
    ================================================================================================
    shop products functions
    ================================================================================================
     */

    fun addToCart(userId: Int, medicineId: Int, quantity:Int): Long{
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

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CART_USER_ID, userId)
        contentValues.put(CART_MEDICINE_ID, medicineId)
        contentValues.put(CART_QUANTITY, quantity)
        contentValues.put(CART_CONFIRMED, "FALSE")
        contentValues.put(CART_DATE, "${hour}:${minuets} $prefix - ${day}/${month}/${year}")
        val success = db.insert(CART_TABLE, null, contentValues)
        db.close()
        return success
    }

    fun getOrderCustomers(): ArrayList<Int> {
        var cartList: ArrayList<Int> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT DISTINCT $CART_USER_ID FROM $CART_TABLE"
        val cursor: Cursor?

        try{
            cursor = db.rawQuery(query, null)
        } catch (e: SQLiteException) {
            return java.util.ArrayList()
        }

        var userId: Int

        if (cursor != null) {
            if(cursor.moveToFirst()){
                do {
                    userId = cursor.getInt(cursor.getColumnIndex(CART_USER_ID))
                    cartList.add(userId)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return cartList
    }

    fun getCartAllTrue():ArrayList<UserCartModel> {
        var cartList: java.util.ArrayList<UserCartModel> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $CART_TABLE WHERE $CART_USER_ID = ${MainActivity.u_id} AND $CART_CONFIRMED = 'TRUE'"
        val cursor: Cursor?

        try{
            cursor = db.rawQuery(query, null)
        } catch (e: SQLiteException) {
            return java.util.ArrayList()
        }

        var id: Int
        var userId: Int
        var medicineId: Int
        var quantity: Int
        var date: String
        var isConfirmed: String

        if (cursor != null) {
            if(cursor.moveToFirst()){
                do {
                    id = cursor.getInt(cursor.getColumnIndex(CART_ID))
                    userId = cursor.getInt(cursor.getColumnIndex(CART_USER_ID))
                    medicineId = cursor.getInt(cursor.getColumnIndex(CART_MEDICINE_ID))
                    date = cursor.getString(cursor.getColumnIndex(CART_DATE))
                    quantity = cursor.getInt(cursor.getColumnIndex(CART_QUANTITY))
                    isConfirmed = cursor.getString(cursor.getColumnIndex(CART_CONFIRMED))
                    val medicine = UserCartModel(_id = id, userId = userId, medicineID = medicineId, quantity = quantity, date = date, isConfirmed = isConfirmed)
                    cartList.add(medicine)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return cartList
    }

    fun getCartAllFalse():ArrayList<UserCartModel> {
        var cartList: java.util.ArrayList<UserCartModel> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $CART_TABLE WHERE $CART_USER_ID = ${MainActivity.u_id} AND $CART_CONFIRMED = 'FALSE'"
        val cursor: Cursor?

        try{
            cursor = db.rawQuery(query, null)
        } catch (e: SQLiteException) {
            return java.util.ArrayList()
        }

        var id: Int
        var userId: Int
        var medicineId: Int
        var quantity: Int
        var date: String
        var isConfirmed: String

        if (cursor != null) {
            if(cursor.moveToFirst()){
                do {
                    id = cursor.getInt(cursor.getColumnIndex(CART_ID))
                    userId = cursor.getInt(cursor.getColumnIndex(CART_USER_ID))
                    medicineId = cursor.getInt(cursor.getColumnIndex(CART_MEDICINE_ID))
                    date = cursor.getString(cursor.getColumnIndex(CART_DATE))
                    quantity = cursor.getInt(cursor.getColumnIndex(CART_QUANTITY))
                    isConfirmed = cursor.getString(cursor.getColumnIndex(CART_CONFIRMED))
                    val medicine = UserCartModel(_id = id, userId = userId, medicineID = medicineId, quantity = quantity, date = date, isConfirmed = isConfirmed)
                    cartList.add(medicine)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return cartList
    }

    fun getCartAll(): java.util.ArrayList<UserCartModel> {
        var cartList: java.util.ArrayList<UserCartModel> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM ${CART_TABLE} WHERE ${CART_USER_ID} = ${MainActivity.u_id}"
        val cursor: Cursor?

        try{
            cursor = db.rawQuery(query, null)
        } catch (e: SQLiteException) {
            return java.util.ArrayList()
        }

        var id: Int
        var userId: Int
        var medicineId: Int
        var quantity: Int
        var date: String
        var isConfirmed: String

        if (cursor != null) {
            if(cursor.moveToFirst()){
                do {
                    id = cursor.getInt(cursor.getColumnIndex(CART_ID))
                    userId = cursor.getInt(cursor.getColumnIndex(CART_USER_ID))
                    medicineId = cursor.getInt(cursor.getColumnIndex(CART_MEDICINE_ID))
                    date = cursor.getString(cursor.getColumnIndex(CART_DATE))
                    quantity = cursor.getInt(cursor.getColumnIndex(CART_QUANTITY))
                    isConfirmed = cursor.getString(cursor.getColumnIndex(CART_CONFIRMED))
                    val medicine = UserCartModel(_id = id, userId = userId, medicineID = medicineId, quantity = quantity, date = date, isConfirmed = isConfirmed)
                    cartList.add(medicine)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return cartList
    }

    fun getCartAll(id:Int): java.util.ArrayList<UserCartModel> {
        var cartList: java.util.ArrayList<UserCartModel> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $CART_TABLE WHERE $CART_USER_ID = $id"
        val cursor: Cursor?

        try{
            cursor = db.rawQuery(query, null)
        } catch (e: SQLiteException) {
            return java.util.ArrayList()
        }

        var id: Int
        var userId: Int
        var medicineId: Int
        var quantity: Int
        var date: String
        var isConfirmed: String

        if (cursor != null) {
            if(cursor.moveToFirst()){
                do {
                    id = cursor.getInt(cursor.getColumnIndex(CART_ID))
                    userId = cursor.getInt(cursor.getColumnIndex(CART_USER_ID))
                    medicineId = cursor.getInt(cursor.getColumnIndex(CART_MEDICINE_ID))
                    date = cursor.getString(cursor.getColumnIndex(CART_DATE))
                    quantity = cursor.getInt(cursor.getColumnIndex(CART_QUANTITY))
                    isConfirmed = cursor.getString(cursor.getColumnIndex(CART_CONFIRMED))
                    val medicine = UserCartModel(_id = id, userId = userId, medicineID = medicineId, quantity = quantity, date = date, isConfirmed = isConfirmed)
                    cartList.add(medicine)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return cartList
    }

    fun getCartOfUser(id:Int): java.util.ArrayList<UserCartModel> {
        var cartList: java.util.ArrayList<UserCartModel> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $CART_TABLE WHERE $CART_ID = $id"
        val cursor: Cursor?

        try{
            cursor = db.rawQuery(query, null)
        } catch (e: SQLiteException) {
            return java.util.ArrayList()
        }

        var id: Int
        var userId: Int
        var medicineId: Int
        var quantity: Int
        var date: String
        var isConfirmed: String

        if (cursor != null) {
            if(cursor.moveToFirst()){
                do {
                    id = cursor.getInt(cursor.getColumnIndex(CART_ID))
                    userId = cursor.getInt(cursor.getColumnIndex(CART_USER_ID))
                    medicineId = cursor.getInt(cursor.getColumnIndex(CART_MEDICINE_ID))
                    date = cursor.getString(cursor.getColumnIndex(CART_DATE))
                    quantity = cursor.getInt(cursor.getColumnIndex(CART_QUANTITY))
                    isConfirmed = cursor.getString(cursor.getColumnIndex(CART_CONFIRMED))
                    val medicine = UserCartModel(_id = id, userId = userId, medicineID = medicineId, quantity = quantity, date = date, isConfirmed = isConfirmed)
                    cartList.add(medicine)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return cartList
    }

    fun updateCartProduct(cart: UserCartModel): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CART_MEDICINE_ID, cart.medicineID)
        contentValues.put(CART_QUANTITY, cart.quantity)
        contentValues.put(CART_USER_ID, cart.userId)
        contentValues.put(CART_DATE, cart.date)
        contentValues.put(CART_CONFIRMED, cart.isConfirmed)
        val success = db.update(CART_TABLE, contentValues, CART_ID + "=" + cart._id, null)
        db.close()
        return success
    }

    fun deleteCart(userCartModel: UserCartModel):Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CART_ID, userCartModel._id)
        val success = db.delete(CART_TABLE, CART_ID + "=" + userCartModel._id, null)
        db.close()
        return success
    }


    //==============================================================================================
    // status
    //==============================================================================================

//    fun setStatus(userId: Int, status: Int): Long{
//        val db = this.writableDatabase
//        val contentValues = ContentValues()
//        contentValues.put(st_id, userId)
//        contentValues.put(st_isLoggedIn, status.toString())
//        val success = db.insert(STATUS_TABLE, null, contentValues)
//        db.close()
//        return success
//    }
//
//    fun getStatus(): ArrayList<userStatus> {
//        val list:ArrayList<userStatus> = ArrayList()
//        val db = this.readableDatabase
//        val query = "SELECT * FROM $STATUS_TABLE"
//        val cursor: Cursor?
//
//        try{
//            cursor = db.rawQuery(query, null)
//        } catch (e: SQLiteException) {
//            return ArrayList()
//        }
//
//        var id: Int
//        var userst: Int
//
//        if (cursor != null) {
//            if(cursor.moveToFirst()){
//                do {
//                    id = cursor.getInt(cursor.getColumnIndex(st_id))
//                    userst = cursor.getInt(cursor.getColumnIndex(st_isLoggedIn))
//                    val model = userStatus(id,userst)
//                    list.add(model)
//
//                } while (cursor.moveToNext())
//            }
//        }
//        cursor.close()
//        db.close()
//        return list
//    }
//
//    fun logout(id:Int):Int{
//        val db = this.writableDatabase
//        val contentValues = ContentValues()
//        contentValues.put(st_isLoggedIn, 0)
//        val success = db.delete(STATUS_TABLE, "$st_userId=$id", null)
//        db.close()
//        return success
//    }

}
