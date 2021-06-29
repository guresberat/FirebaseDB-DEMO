package com.guresberat.myfirebasedbexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.guresberat.myfirebasedbexercise.fragments.ListFragment
import com.guresberat.myfirebasedbexercise.fragments.RegisterFragment
import com.guresberat.myfirebasedbexercise.fragments.adapter.ViewPagerAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var demoCollectionAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.viewPager)

        setUp()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        }
        )
    }

    private fun setUp() {

        val fragmentManager = supportFragmentManager
        val lifecycle = lifecycle
        val fragments = ArrayList<Fragment>()
        val registerFragment = RegisterFragment()
        val listFragment = ListFragment()

        fragments.add(registerFragment)
        fragments.add(listFragment)
        demoCollectionAdapter = ViewPagerAdapter(fragmentManager, lifecycle, fragments)
        viewPager.adapter = demoCollectionAdapter
        tabLayout.addTab(tabLayout.newTab().setText("Register"))
        tabLayout.addTab(tabLayout.newTab().setText("List"))

        val tablistener = object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        }

        tabLayout.addOnTabSelectedListener(tablistener)

    }
}