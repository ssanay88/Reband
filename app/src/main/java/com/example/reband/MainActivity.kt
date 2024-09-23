package com.example.reband

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reband.databinding.ActivityMainBinding
import com.example.reband.fragment.HiringFragment
import com.example.reband.fragment.HomeFragment
import com.example.reband.fragment.MyPageFragment
import com.example.reband.fragment.PoolFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView

class MainActivity : AppCompatActivity() {

    private val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(mainBinding.root)

        init()

        // 처음 화면을 HomeFragment로 설정
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, HomeFragment()).commit()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun init() {
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        mainBinding.mainBottomNavigation.apply {

            // 바텀 네비게이션의 가운데 버튼을 커스텀
            val bottomNavigationMenuView = getChildAt(0) as BottomNavigationMenuView
            val addBtnView = bottomNavigationMenuView.getChildAt(2) as BottomNavigationItemView
            val newAddBtnView = LayoutInflater.from(this@MainActivity)
                .inflate(R.layout.bottom_navigation_add_btn, this, false)
            addBtnView.addView(newAddBtnView)

            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_fragment_container, HomeFragment()).commit()
                        true
                    }

                    R.id.nav_hiring -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_fragment_container, HiringFragment()).commit()
                        true
                    }

                    R.id.nav_add -> {
                        Log.d("tngur", "initBottomNavigation")
                        true
                    }

                    R.id.nav_pool -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_fragment_container, PoolFragment()).commit()
                        true
                    }

                    R.id.nav_my_page -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_fragment_container, MyPageFragment()).commit()
                        true
                    }

                    else -> false
                }
            }
        }
    }

}