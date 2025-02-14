package com.project.reband

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK

class GlobalApplication : Application() {

    private lateinit var dataStore : DataStoreRepository

    companion object {
        private lateinit var globalApplication: GlobalApplication
        fun getInstance() : GlobalApplication = globalApplication
    }

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "b27a74b026594e2bc6ddc61b64837bd8")
        NaverIdLoginSDK.initialize(this, getString(R.string.naver_client_id), getString(R.string.naver_client_secret), getString(R.string.naver_client_name))
        globalApplication = this
        dataStore = DataStoreRepository(this)
    }

    fun getDataStore() : DataStoreRepository = dataStore

}