package com.nlv2022.trenaghorik.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nlv2022.trenaghorik.R

object FragmentManager { // внимание при использовнии!!! такой же класс есть в Андроид

    var currentFragment: Fragment? = null

    fun setFragment(newFragment: Fragment, act: AppCompatActivity) {
        val transaction = act.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)

        transaction.replace(R.id.placeHolder, newFragment)
        transaction.commit()
        currentFragment = newFragment

    }

}