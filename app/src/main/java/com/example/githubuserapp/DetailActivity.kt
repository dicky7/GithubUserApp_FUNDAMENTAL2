package com.example.githubuserapp

import android.os.Bundle
import android.widget.TableLayout
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.adapter.SectionPager
import com.example.githubuserapp.databinding.ActivityDetailBinding
import com.example.githubuserapp.model.Person
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
        const val EXTRA_PERSON = "person"
    }
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val person = intent.getParcelableExtra<Person>(EXTRA_PERSON) as Person
        binding.detailName.text = person.login_username
        binding.detailCompany.text = person.company
        binding.detailFollowers.text = person.follower.toString()
        binding.detailFollowing.text = person.following.toString()
        binding.detailLocation.text = person.location
        binding.detailRepotories.text = person.repository.toString()
        Glide.with(this)
            .load(person.avatar_url)
            .into(binding.detailAvatar)

        val sectionPager = SectionPager(this,supportFragmentManager)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPager
        val tabs: TabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabs,viewPager){tab,position->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }
}