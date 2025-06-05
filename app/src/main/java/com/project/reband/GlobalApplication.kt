package com.project.reband

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlobalApplication : Application() {

    private lateinit var dataStore : DataStoreRepository

    companion object {
        private lateinit var globalApplication: GlobalApplication
        fun getInstance() : GlobalApplication = globalApplication
    }

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
        NaverIdLoginSDK.initialize(this, BuildConfig.NAVER_CLIENT_ID, BuildConfig.NAVER_CLIENT_SECRET, BuildConfig.NAVER_CLIENT_NAME)
        globalApplication = this
        dataStore = DataStoreRepository(this)
    }

    fun getDataStore() : DataStoreRepository = dataStore

}