package com.jnu.win7gm.myaccount.ui.home

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jnu.win7gm.myaccount.R
import com.jnu.win7gm.myaccount.data.entity.Record
import com.jnu.win7gm.myaccount.databinding.FragmentHomeBinding
import com.jnu.win7gm.myaccount.util.RecordAdapter
import com.jnu.win7gm.myaccount.viewmodel.SharedViewModel
import java.util.*


class HomeFragment : Fragment(), RecordAdapter.OnItemLongPressedListener {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val vm: SharedViewModel by activityViewModels()

    private fun initToolbar() {
        val navController = findNavController()
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_history, R.id.nav_analysis
            ), requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
        )

        binding.toolbar
            .setupWithNavController(navController, appBarConfiguration)

        // TODO modify menu
        binding.toolbar.inflateMenu(R.menu.home_menu)
        binding.toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }
    }

    private fun initRecyclerView() {
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = Adapter()
    }

    private fun initFab() {
        binding.homeFab.setOnClickListener {
            // navigate to AddRecordFragment with nav ui, param is id for action or destination in nav graph
            findNavController().navigate(R.id.nav_add_record)
        }
    }

    private fun initHeader() {
        var sumOfPlus = 0.00
        var sumOfMinus = 0.00

        vm.recordList.forEach {
            if (it.type == '+')
                sumOfPlus += it.money
            else
                sumOfMinus += it.money
        }
        binding.budgetBalanceNum.text = String.format("%.2f", (sumOfPlus - sumOfMinus))
        binding.textInSum.text = String.format("%.2f", sumOfPlus)
        binding.textOutSum.text = String.format("%.2f", sumOfMinus)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initToolbar()
        initRecyclerView()
        initFab()
        initHeader()

        return root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_share_text -> {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "总收入：" + binding.textInSum.text + " 总支出：" + binding.textOutSum.text
                    )
                    type = "text/plain"
                }
                requireContext().startActivity(Intent.createChooser(sendIntent, "分享到..."))
                return true
            }
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    inner class Adapter : RecyclerView.Adapter<Adapter.BriefViewHolder>() {

        private var daysAvailable: MutableList<String>
//        private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        init {
            daysAvailable = getAvailableDays()
        }

        private fun getAvailableDays(): MutableList<String> {
            val tmp = mutableSetOf<String>()
            vm.recordList.forEach {
                tmp.add(it.date)
            }
            return tmp.sorted().reversed().toMutableList()
        }

        inner class BriefViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val dateTextView: TextView = view.findViewById(R.id.date_text)
            val inTextView: TextView = view.findViewById(R.id.income_text)
            val outTextView: TextView = view.findViewById(R.id.outcome_text)
            val briefList: RecyclerView = view.findViewById(R.id.record_brief)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BriefViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_recycler_item, parent, false)

            return BriefViewHolder(view)
        }

        override fun onBindViewHolder(holder: BriefViewHolder, position: Int) {
            val date = daysAvailable[position]
            val listOfThisDay = mutableListOf<Record>()
            vm.recordList.forEach {
                if (it.date == date)
                    listOfThisDay.add(it)
            }

            var sumOfPlus = 0.00
            var sumOfMinus = 0.00

            listOfThisDay.forEach {
                if (it.type == '+')
                    sumOfPlus += it.money
                else
                    sumOfMinus += it.money
            }

            listOfThisDay.sortBy {
                it.time
            }

            holder.dateTextView.text = date
            holder.inTextView.text = "收入：" + String.format("%.2f", sumOfPlus)
            holder.outTextView.text = "支出：" + String.format("%.2f", sumOfMinus)
            holder.briefList.adapter = RecordAdapter(listOfThisDay, vm.getCategoryMap())
            (holder.briefList.adapter as RecordAdapter).setOnItemLongPressedListener(this@HomeFragment)
            holder.briefList.layoutManager = LinearLayoutManager(requireContext())
        }

        override fun getItemCount(): Int {
            return daysAvailable.size
        }
    }

    var recordPressedId: UUID? = null

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.rec_delete -> {
                val alertDB: AlertDialog.Builder = AlertDialog.Builder(this.requireContext())
                alertDB.setPositiveButton("是") { _: DialogInterface, _: Int ->
                    var rec: Record? = null
                    for (it in vm.recordList) {
                        if (it.id == recordPressedId) {
                            rec = it
                            break
                        }
                    }
                    vm.recordList.remove(rec)
                    vm.dataBank.saveRecord()
                    when (rec?.type) {
                        '+' -> {
                            binding.budgetBalanceNum.text =
                                String.format(
                                    "%.2f",
                                    binding.budgetBalanceNum.text.toString().toDouble() - rec?.money
                                )
                            binding.textInSum.text =
                                String.format(
                                    "%.2f",
                                    binding.textInSum.text.toString().toDouble() - rec?.money
                                )
                        }
                        '-' -> {
                            binding.budgetBalanceNum.text =
                                String.format(
                                    "%.2f",
                                    binding.budgetBalanceNum.text.toString().toDouble() + rec?.money
                                )
                            binding.textOutSum.text =
                                String.format(
                                    "%.2f",
                                    binding.textOutSum.text.toString().toDouble() - rec?.money
                                )
                        }
                    }
                    binding.list.adapter!!.notifyDataSetChanged()
                    //initHeader()
                }
                alertDB.setNegativeButton("否") { _: DialogInterface, _: Int ->
                }
                alertDB.setMessage("你确定要删除这条记录吗？")
                alertDB.setTitle("警告").show()
                return true
            }
        }
        return false
    }

    override fun onItemLongPressed(id: UUID) {
        recordPressedId = id
    }
}