package com.example.paymentsappfortest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paymentsappfortest.R
import com.example.paymentsappfortest.data.Payments
import com.example.paymentsappfortest.data.PaymentsResponse
import kotlinx.android.synthetic.main.item.view.*

class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {
    private var paymentsList = emptyList<Payments>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item,
                        parent,
                        false
                )

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ItemViewHolder, position: Int) {

        val currentItem = paymentsList[position]

        holder.textViewDesc.text = currentItem.desc
        holder.textViewAmount.text = currentItem.amount.toString()
        holder.textViewCurrency.text = currentItem.currency
        holder.textViewCreated.text = currentItem.created.toString()

    }

    override fun getItemCount() = paymentsList.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewDesc: TextView = itemView.desc_text_view
        val textViewAmount: TextView = itemView.amount_text_view
        val textViewCurrency: TextView = itemView.currency_text_view
        val textViewCreated: TextView = itemView.created_text_view

    }

    fun setData(paymentsResponse: PaymentsResponse?) {
        val list = paymentsResponse?.payments
        if (list != null) {
            this.paymentsList = list
        }
        notifyDataSetChanged()
    }
}