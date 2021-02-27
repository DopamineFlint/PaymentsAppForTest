package com.example.paymentsappfortest.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paymentsappfortest.R
import com.example.paymentsappfortest.api.RetrofitInstance
import com.example.paymentsappfortest.data.PaymentsResponse
import kotlinx.android.synthetic.main.fragment_payments.*
import kotlinx.android.synthetic.main.fragment_payments.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PaymentsFragment : Fragment(R.layout.fragment_payments) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("FragmentsLog", "Payments Fragment Created")

        val paymentsAdapter = RecyclerViewAdapter()
        val recyclerView = view.recycler_view
        recyclerView.adapter = paymentsAdapter
        recyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
        )

        GlobalScope.launch(Dispatchers.IO) {
            val response = RetrofitInstance.api.getPayments()
            if (response.isSuccessful) {
                val payments: PaymentsResponse? = response.body()
                launch(Dispatchers.Main) {
                    paymentsAdapter.setData(payments)
                }
            }
        }

        button_logout.setOnClickListener {
            val preferences: SharedPreferences =
                    androidx.preference.PreferenceManager.getDefaultSharedPreferences(
                            requireContext()
                    )
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putString("remember", "false")
            editor.apply()

            val action = PaymentsFragmentDirections.actionPaymentsFragmentToLogInFragment()
            findNavController().navigate(action)
        }
    }
}