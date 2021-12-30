package com.jnu.win7gm.myaccount.ui.addrecord

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.jnu.win7gm.myaccount.data.entity.Record
import com.jnu.win7gm.myaccount.databinding.RecordAdderBinding
import com.jnu.win7gm.myaccount.util.CategoryAdapter
import com.jnu.win7gm.myaccount.viewmodel.SharedViewModel
import java.text.SimpleDateFormat
import java.util.*


class AdderFragment : Fragment() {
    //    private var db: AppDatabase? = null
    private var _binding: RecordAdderBinding? = null
    private var type: Int? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
    private val timeFormatter = SimpleDateFormat("HH:mm:ss")
    private val vm: SharedViewModel by activityViewModels()

    private fun initRecyclerView() {
        when (type) {
            0 -> binding.categoryList.adapter = CategoryAdapter(vm.categoryList.filter {
                it.defaultType == '-'
            })
            1 -> binding.categoryList.adapter = CategoryAdapter(vm.categoryList.filter {
                it.defaultType == '+'
            })
        }
        binding.categoryList.layoutManager = GridLayoutManager(requireContext(), 5)
        // 拿到选择的信息并实时显示
        (binding.categoryList.adapter as CategoryAdapter).setOnSelectedChangedListener {
            binding.categoryText.text = it.name
        }
    }

    private fun initEditText() {
        val manager: InputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.moneyEdit.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    val now = Date()
                    val rec: Record
                    var exocc = false
                    try {
                        rec = Record(
                            UUID.randomUUID(),
                            dateFormatter.format(now),
                            timeFormatter.format(now),
                            "现金",
                            (binding.categoryList.adapter as CategoryAdapter).selectedRef!!.name,
                            when (type) {
                                0 -> '-'
                                else -> '+'
                            },
                            v.text.toString().toDouble(),
                            ""
                        )
                        vm.recordList.add(rec)
                        vm.dataBank.saveRecord()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(requireContext(), "信息不完整或有误，无法创建记录", Toast.LENGTH_SHORT)
                            .show()
                        exocc = true
                    }

                    if (!exocc) {
                        findNavController().navigateUp()
                        manager.hideSoftInputFromWindow(
                            requireView().windowToken,
                            InputMethodManager.HIDE_NOT_ALWAYS
                        )
                    }
                    true
                }
                else -> false
            }
        }

        binding.moneyEdit.requestFocus()
        manager.showSoftInput(binding.moneyEdit, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = RecordAdderBinding.inflate(inflater, container, false)
        val root: View = binding.root
        type = (requireArguments()["type"] as Int)
        initRecyclerView()
        initEditText()
        return root
    }

}