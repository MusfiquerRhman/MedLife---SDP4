package com.example.medlife

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class profile : Fragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileName = activity?.findViewById<TextView>(R.id.profileName)
        val profileEmail = activity?.findViewById<TextView>(R.id.profileEmail)
        val profileDateOfBirth = activity?.findViewById<TextView>(R.id.profileDateOfBirth)
        val profileAddress = activity?.findViewById<TextView>(R.id.profileAddress)
        val profilePhoneNo = activity?.findViewById<TextView>(R.id.profilePhoneNo)
        val profileUserType = activity?.findViewById<TextView>(R.id.profileUserType)


        if (profileEmail != null) {
            profileEmail.text = home_page.u_email
        }
        if (profileName != null) {
            profileName.text = home_page.u_name
        }
        if (profileDateOfBirth != null) {
            profileDateOfBirth.text = home_page.u_dateOfBirth
        }
        if (profileAddress != null) {
            profileAddress.text = home_page.u_address
        }
        if (profilePhoneNo != null) {
            profilePhoneNo.text  = home_page.u_phoneNo
        }
        if (profileUserType != null) {
            profileUserType.text = home_page.u_userType
        }

        activity?.findViewById<Button>(R.id.ordersButton)?.setOnClickListener{
            startActivity(Intent(requireContext(), userPendingOrders::class.java))
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}