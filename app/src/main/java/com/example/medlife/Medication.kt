package com.example.medlife

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Medication.newInstance] factory method to
 * create an instance of this fragment.
 */
class Medication : Fragment(), CartRecylerAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medication, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = UserDatabase(requireContext(), null)
        val list: ArrayList<UserCartModel> = db.getCartAllFalse()

        //val data: ArrayList<MedicineShopModel> = db.getMedicine()
        val cartAdapter = CartRecylerAdapter(requireContext(), list, this)
        val cartStatusView = activity?.findViewById<RecyclerView>(R.id.cartItems)
        val cartInfo = activity?.findViewById<TextView>(R.id.CartInfo)
        var totalMed = 0
        var totalPrice = 0.0
        for(item in list){
            totalMed += item.quantity
            totalPrice += db.getMedicine(item.medicineID)[0].price * item.quantity
        }

        if (cartInfo != null) {
            cartInfo.text = "${totalMed} items: ${totalPrice} TK"
        }

        if (cartStatusView != null) {
            cartStatusView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            cartStatusView.adapter = cartAdapter
        }

        activity?.findViewById<Button>(R.id.cartOderSubmitButton)?.setOnClickListener{
            if(list.isEmpty()){
                Toast.makeText(requireContext(), "Your cart is empty!", Toast.LENGTH_SHORT).show()
            } else {
                for(i in 0 until list.size){
                    if(list[i].isConfirmed == "FALSE"){
                        list[i].isConfirmed = "TRUE"
                        db.updateCartProduct(list[i])
                    }
                }
                cartAdapter.notifyDataSetChanged()

                val ft = requireFragmentManager().beginTransaction()
                if (Build.VERSION.SDK_INT >= 26) {
                    ft.setReorderingAllowed(false)
                }
                ft.detach(this).attach(this).commit()
            }
        }

        db.close()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Medication.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Medication().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(position: Int) {
        val shop = UserDatabase(requireContext(), null)
        val cartList:ArrayList<UserCartModel> = shop.getCartAll()
        val clickedItem = cartList[position]._id
        val intent = Intent(activity, cartEdit::class.java)
        intent.putExtra("CartID", clickedItem)
        startActivity(intent)
    }
}