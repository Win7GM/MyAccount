package com.jnu.win7gm.myaccount.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jnu.win7gm.myaccount.R
import com.jnu.win7gm.myaccount.data.entity.Record

class RecordAdapter(private val records: List<Record>) :
    RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

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
        // TODO set category icon
        holder.typeImageView.setImageResource(R.drawable.ic_default_category)
        holder.typeTextView.text = records[position].category
        holder.accountTextView.text = records[position].account
        holder.moneyTextView.text =
            records[position].type.toString() + records[position].money.toString()
    }

    override fun getItemCount(): Int {
        return records.size
    }
}