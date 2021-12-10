package com.jnu.win7gm.myaccount.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.jnu.win7gm.myaccount.R
import com.jnu.win7gm.myaccount.data.database.AppDatabase
import com.jnu.win7gm.myaccount.data.entity.DateClass
import com.jnu.win7gm.myaccount.databinding.FragmentHomeBinding
import com.jnu.win7gm.myaccount.util.RecordAdapter
import java.text.SimpleDateFormat

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = AppDatabase.getInstance(requireContext())
    }

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

        binding.toolbar.inflateMenu(R.menu.home_menu)
    }

    private fun initRecyclerView() {
        binding.list.adapter = Adapter(db!!)
    }

    private fun initFab() {
        binding.homeFab.setOnClickListener {
            //TODO add record
            findNavController().navigate(R.id.nav_add_record)
        }
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
        return root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class Adapter(private val db: AppDatabase) : RecyclerView.Adapter<Adapter.BriefViewHolder>() {

        private var daysAvailable: List<DateClass>
        private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        init {
            daysAvailable = db.recordDao().getAvailableDate()
            if (!db.isOpen)
                throw RuntimeException("database connection not open")
        }

        class BriefViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
            val date = daysAvailable[position].date!!
            holder.dateTextView.text = simpleDateFormat.format(date)
            holder.inTextView.text = db.recordDao().sumByDateAndType(date, '+').toString()
            holder.outTextView.text = db.recordDao().sumByDateAndType(date, '-').toString()
            holder.briefList.adapter = RecordAdapter(db.recordDao().getByDate(date))
        }

        override fun getItemCount(): Int {
            return daysAvailable.size
        }
    }
}