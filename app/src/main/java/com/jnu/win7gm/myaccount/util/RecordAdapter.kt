package com.jnu.win7gm.myaccount.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jnu.win7gm.myaccount.R

class RecordAdapter : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    class RecordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val typeImageView: ImageView = view.findViewById(R.id.type_image)
        val typeTextView: TextView = view.findViewById(R.id.type_text)
        val accountTextView: TextView = view.findViewById(R.id.account_text)
        val moneyTextView: TextView = view.findViewById(R.id.money_text)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_brief_info, parent, false)

        return RecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        TODO("DAO Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("DAO Not yet implemented")
    }
}