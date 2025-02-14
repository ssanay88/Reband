package com.project.reband

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.project.reband.databinding.ActivityMainBinding
import com.project.reband.fragment.HiringFragment
import com.project.reband.fragment.HomeFragment
import com.project.reband.fragment.MyPageFragment
import com.project.reband.fragment.PoolFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.gson.Gson
import com.kakao.sdk.auth.AuthApiClient
import com.project.reband.data.etc.HashTagData
import com.project.reband.data.etc.InstrumentData
import com.project.reband.data.etc.LocationSecondDepth
import com.project.reband.fragment.BandRegisterFragment
import com.project.reband.fragment.ConfirmDialog
import com.project.reband.fragment.ErrorDialog
import com.project.reband.fragment.HiringRegisterFragment
import com.project.reband.fragment.ProfileRegisterFragment
import com.project.reband.viewmodel.MainViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val dataStore = GlobalApplication.getInstance().getDataStore()
    private val viewModel by viewModels<MainViewModel>()

    private var hashTagList: MutableList<HashTagData.HashTagList.HashTag> = mutableListOf()
    private var instrumentList: MutableList<InstrumentData.InstrumentList.Instrument> = mutableListOf()
    private var isFabVisible = false

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
        initInstrumentList()
        initHashTagList()
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
                        if (isFabVisible) {
                            mainBinding.fabRegisterPool.hide()
                            mainBinding.fabRecruitMember.hide()
                            isFabVisible = false
                        } else {
                            mainBinding.fabRegisterPool.show()
                            mainBinding.fabRecruitMember.show()
                            isFabVisible = true
                        }
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

        mainBinding.fabRecruitMember.setOnClickListener {
            // 밴드원 모집
            lifecycleScope.launch {
                if (AuthApiClient.instance.hasToken()) {
                    dataStore.bandNo.collect {
                        if (it.isNotEmpty()) {
                            dataStore.userGrade.collect {
                                if (it == "LEADER") {
                                    supportFragmentManager.beginTransaction()
                                        .replace(R.id.main_fragment_container, HiringRegisterFragment()).commit()
                                } else {
                                    val errorDialog = ErrorDialog(
                                        title = "오류",
                                        content = "권한이 없습니다.\n밴드장만 이용 가능한 메뉴입니다."
                                    )
                                    errorDialog.show(supportFragmentManager, "errorDialog")
                                }
                            }
                        } else {
                            val confirmDialog = ConfirmDialog(
                                title = "확인",
                                content = "소속된 밴드가 없습니다.\n밴드를 등록하시겠습니까?",
                                confirmCallback = {
                                    supportFragmentManager.beginTransaction()
                                        .replace(R.id.main_fragment_container, BandRegisterFragment()).commit()
                                }
                            )
                            confirmDialog.show(supportFragmentManager, "confirmDialog")
                        }
                    }
                } else {
                    val errorDialog = ErrorDialog(
                        title = "오류",
                        content = "로그인 후 이용해주세요"
                    )
                    errorDialog.show(supportFragmentManager, "errorDialog")
                }
            }
        }

        mainBinding.fabRegisterPool.setOnClickListener {
            // 인재풀 등록
            // 가입한 밴드가 있는 경우 -> 이미 가입한 밴드 있음
            // 없는 경우 -> 프로필 정보 등록 유무 -> 인재풀에 등록된 경우 , 신규 등록인 경우 분리
            // 프로필 정보 등록이 되지 않은 경우
            lifecycleScope.launch {
                if (AuthApiClient.instance.hasToken()) {
                    dataStore.bandNo.collect {
                        if (it.isNotEmpty()) {
                            val errorDialog = ErrorDialog(
                                title = "오류",
                                content = "이미 가입된 밴드가 있습니다."
                            )
                            errorDialog.show(supportFragmentManager, "errorDialog")
                        } else {
                            dataStore.instrument.collectLatest {
                                if (it.isEmpty()) {
                                    val confirmDialog = ConfirmDialog(
                                        title = "확인",
                                        content = "프로필 등록 후 이용 가능합니다.\n지금 등록하시겠습니까?",
                                        confirmCallback = {
                                            supportFragmentManager.beginTransaction()
                                                .replace(R.id.main_fragment_container, ProfileRegisterFragment()).commit()
                                        }
                                    )
                                    confirmDialog.show(supportFragmentManager, "confirmDialog")
                                } else {
                                    val confirmDialog = ConfirmDialog(
                                        title = "확인",
                                        content = "현재 인재풀에 등록된 상태입니다.\n최신글로 갱신하시겠습니까?",
                                        confirmCallback = {
                                            // 갱신
                                        }

                                    )
                                    confirmDialog.show(supportFragmentManager, "errorDialog")
                                }

                            }
                        }
                    }

                } else {
                    val errorDialog = ErrorDialog(
                        title = "오류",
                        content = "로그인 후 이용해주세요"
                    )
                    errorDialog.show(supportFragmentManager, "errorDialog")
                }
            }

        }
    }

    private fun initInstrumentList() {
        lifecycleScope.launch {
            viewModel.getInstrumentList()
            viewModel.instrumentList.collectLatest {
                it?.instrumentList?.forEach { instrument ->
                    instrumentList.add(instrument)
                }
            }
        }
    }

    private fun initHashTagList() {
        lifecycleScope.launch {
            viewModel.getHashTagList()
            viewModel.hashTagList.collectLatest {
                it?.hashTagList?.forEach { hashTag ->
                    hashTagList.add(hashTag)
                }
            }
        }
    }
}