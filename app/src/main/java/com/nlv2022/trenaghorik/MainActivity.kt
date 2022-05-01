package com.nlv2022.trenaghorik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.nlv2022.trenaghorik.Fragments.DaysFragment
import com.nlv2022.trenaghorik.utils.FragmentManager
import com.nlv2022.trenaghorik.utils.MainViewModel

class MainActivity : AppCompatActivity() {

    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model.pref = getSharedPreferences("main", MODE_PRIVATE)

        FragmentManager.setFragment(DaysFragment.newInstance(), this)
    }

    override fun onBackPressed() {
        if (FragmentManager.currentFragment is DaysFragment) {
            super.onBackPressed()
        } else FragmentManager.setFragment(DaysFragment.newInstance(), this)


    }
}