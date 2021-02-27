package com.example.paymentsappfortest.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paymentsappfortest.R
import com.example.paymentsappfortest.api.RetrofitInstance
import com.example.paymentsappfortest.data.PaymentsResponse
import kotlinx.android.synthetic.main.fragment_payments.*
import kotlinx.android.synthetic.main.fragment_payments.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val call: Call<PaymentsResponse> = RetrofitInstance.api.getPayments()
        call.enqueue(object : Callback<PaymentsResponse> {
            override fun onResponse(call: Call<PaymentsResponse>, response: Response<PaymentsResponse>) {
                if (!response.isSuccessful) {
                    return
                }

                val payments: PaymentsResponse? = response.body()
                paymentsAdapter.setData(payments)
            }

            override fun onFailure(call: Call<PaymentsResponse>, t: Throwable) {
                Log.d(LogInFragment.NAME_LOG, "ERROR " + t.message!!)
            }
        })

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