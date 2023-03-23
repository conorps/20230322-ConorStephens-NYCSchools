package com.example.nycschools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nycschools.ui.list.SchoolListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SchoolListFragment.newInstance())
                .commitNow()
        }
    }
}