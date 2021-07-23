package com.example.githubuserapp.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuserapp.fragment.FollowersFragment
import com.example.githubuserapp.fragment.FollowingFragment

class SectionPager(activity: AppCompatActivity, supportFragmentManager: FragmentManager) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0->fragment = FollowersFragment()
            1->fragment = FollowingFragment()
        }
        return fragment as Fragment
    }
}