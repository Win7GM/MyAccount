package com.jnu.win7gm.myaccount.ui.addrecord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.jnu.win7gm.myaccount.R
import com.jnu.win7gm.myaccount.data.database.AppDatabase
import com.jnu.win7gm.myaccount.databinding.FragmentAddRecordBinding

class AddRecordFragment : Fragment() {
    private var db: AppDatabase? = null
    private var _binding: FragmentAddRecordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentAddRecordBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initToolbar()
        return root
    }


}