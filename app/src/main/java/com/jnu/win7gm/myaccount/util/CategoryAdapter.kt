package com.jnu.win7gm.myaccount.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jnu.win7gm.myaccount.data.entity.Category
import com.jnu.win7gm.myaccount.databinding.CategoryListItemBinding

class CategoryAdapter(private val categories: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    class CategoryViewHolder(_binding: ViewBinding) : RecyclerView.ViewHolder(_binding.root) {
        var binding: CategoryListItemBinding = _binding as CategoryListItemBinding

        var isChecked = false
    }

    private var selected: Int = -1
    private var lastSelected: Int = -1
    var selectedRef: Category? = null
    private var mOnSelectedChangedListener: OnSelectedChangedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding =
            CategoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.categoryIc.setImageResource(categories[position].icon)
        holder.binding.categoryName.text = categories[position].name
        holder.isChecked = holder.layoutPosition == selected
        holder.binding.check.visibility = View.GONE

        if (holder.isChecked) {
            selectedRef = categories[position]
            holder.binding.check.visibility = View.VISIBLE
        }

        holder.binding.layout.setOnClickListener {
            holder.isChecked = true
            holder.binding.check.visibility = View.VISIBLE
            lastSelected = selected
            selected = holder.layoutPosition
            selectedRef = categories[position]
            mOnSelectedChangedListener?.onSelectedChanged(selectedRef!!)
            notifyItemChanged(lastSelected)
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun interface OnSelectedChangedListener {
        fun onSelectedChanged(ctg: Category)
    }

    fun setOnSelectedChangedListener(l: OnSelectedChangedListener?) {
        mOnSelectedChangedListener = l
    }
}