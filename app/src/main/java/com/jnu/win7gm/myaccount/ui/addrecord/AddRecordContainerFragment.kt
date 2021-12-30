package com.jnu.win7gm.myaccount.ui.addrecord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jnu.win7gm.myaccount.R
import com.jnu.win7gm.myaccount.databinding.FragmentAddRecordBinding
import com.jnu.win7gm.myaccount.viewmodel.SharedViewModel

class AddRecordContainerFragment : Fragment() {
    private var _binding: FragmentAddRecordBinding? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewpager2Content.adapter = VPAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewpager2Content) { tab, position ->
            when (position) {
                0 -> tab.text = "支出"
                1 -> tab.text = "收入"
            }
        }.attach()
    }

    class VPAdapter(val fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            // Return a NEW fragment instance in createFragment(int)
            val newfrg = AdderFragment()
            newfrg.arguments = Bundle().apply {
                // 0 for outcome 1 for income
                putInt("type", position)
            }
            return newfrg
        }
    }

}