package com.example.paymentsappfortest.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.paymentsappfortest.MainActivity
import com.example.paymentsappfortest.R
import com.example.paymentsappfortest.api.RetrofitInstance
import com.example.paymentsappfortest.data.LoginResponse
import com.example.paymentsappfortest.data.PaymentsResponse
import kotlinx.android.synthetic.main.fragment_log_in.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogInFragment : Fragment(R.layout.fragment_log_in) {

    companion object {
        const val NAME_LOG = "PaymentLog"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("FragmentsLog", "Login Fragment Created")

        val preferences: SharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(
                        requireContext()
                )
        val checkPreLogin = preferences.getString("remember", "")

        if (checkPreLogin.equals("true")) {
            val preAction = LogInFragmentDirections.actionLogInFragmentToPaymentsFragment()
            val navController = findNavController()
            navController.navigate(preAction)
        }

        button_log_in.setOnClickListener {
            val textLogin: String = edit_text_login.text.toString()
            val textPassword = edit_text_password.text.toString()

            checkPass(textLogin, textPassword)
        }
    }

    private fun checkPass(l: String, p: String) {
        val login: RequestBody = l.toRequestBody(l.toMediaTypeOrNull())
        val password: RequestBody = p.toRequestBody(p.toMediaTypeOrNull())

        val call: Call<LoginResponse> = RetrofitInstance.api.login(login, password)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if (!response.isSuccessful) {
                    Log.d(NAME_LOG, "NOT SUCCESSFUL ${response.code()}")
                    return
                }

                val token: LoginResponse? = response.body()

                if (token?.success == true) {
                    Log.d(NAME_LOG, "SUCCESS " + " ${token.success}")

                    val preferences: SharedPreferences =
                        androidx.preference.PreferenceManager.getDefaultSharedPreferences(
                                requireContext()
                        )
                    val editor: SharedPreferences.Editor = preferences.edit()
                    editor.putString("remember", "true")
                    editor.apply()

                    val action = LogInFragmentDirections.actionLogInFragmentToPaymentsFragment() //
                    findNavController().navigate(action) //
                } else {
                    Toast.makeText(
                            requireContext(),
                            "Response error or wrong login and password",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d(NAME_LOG, "ERROR " + t.message!!)
                return
            }
        })
    }

}