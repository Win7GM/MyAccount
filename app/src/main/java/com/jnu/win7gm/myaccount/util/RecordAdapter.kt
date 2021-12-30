package com.jnu.win7gm.myaccount.util

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jnu.win7gm.myaccount.R
import com.jnu.win7gm.myaccount.data.entity.Category
import com.jnu.win7gm.myaccount.data.entity.Record
import com.jnu.win7gm.myaccount.databinding.RecordBriefInfoBinding
import java.util.*

class RecordAdapter(
    private val records: MutableList<Record>,
    private val categories: Map<String, Category>
) : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    private var mOnItemLongPressedListener: OnItemLongPressedListener? = null

    inner class RecordViewHolder(_binding: ViewBinding) : RecyclerView.ViewHolder(_binding.root),
        View.OnCreateContextMenuListener {
        // TODO 视图绑定需要在外面直接初始化
        var binding: RecordBriefInfoBinding = _binding as RecordBriefInfoBinding

        init {
            binding.root.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
//            menu?.setHeaderTitle("记录")
            val inflater = MenuInflater(binding.root.context)
            inflater.inflate(R.menu.record_menu, menu)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        // Create a new view, which defines the UI of the list item false)
        val binding =
            RecordBriefInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        categories[records[position].category]?.let {
            holder.binding.typeImage.setImageResource(
                it.icon
            )
        }
        // save id for ease of modifying
        holder.binding.moneyText.tag = records[position].id
        holder.binding.typeText.text = records[position].category
        holder.binding.accountText.text = records[position].account
        holder.binding.moneyText.text =
            records[position].type.toString() + String.format("%.2f", records[position].money)
        holder.itemView.setOnLongClickListener {
            mOnItemLongPressedListener?.onItemLongPressed(records[position].id)
            false
        }

    }

    override fun getItemCount(): Int {
        return records.size
    }

    fun interface OnItemLongPressedListener {
        fun onItemLongPressed(id: UUID)
    }

    fun setOnItemLongPressedListener(l: OnItemLongPressedListener) {
        mOnItemLongPressedListener = l
    }

}