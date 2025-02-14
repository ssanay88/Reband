package com.project.reband.network.etc

import android.util.Log
import com.project.reband.RetrofitService
import com.project.reband.data.etc.HashTagData
import com.project.reband.data.etc.InstrumentData
import com.project.reband.data.etc.NoticeData
import com.project.reband.data.etc.NotificationData
import com.project.reband.data.etc.TermsOfServiceData
import com.project.reband.data.user.UserInfoData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EtcRepository {
    private val apis = RetrofitService.retrofit.create(EtcService::class.java)

    /**
     * 유저 정보
     */
    suspend fun getUserInfoKakao(accessToken: String, refreshToken: String): Flow<UserInfoData> = flow {
        val res = apis.getUserInfoKakao(accessToken, refreshToken)
        if (res.isSuccessful) {
            res.body()?.let {
                emit(it)
            }
        }
    }

    suspend fun getUserInfoNaver(accessToken: String, refreshToken: String): Flow<UserInfoData> = flow {
        val res = apis.getUserInfoNaver(accessToken, refreshToken)
        if (res.isSuccessful) {
            res.body()?.let {
                emit(it)
            }
        }
    }

    /**
     * 약관 조회
     */
    suspend fun getTermsOfService(): Flow<TermsOfServiceData> = flow {
        val res = apis.getTermsOfService()
        if (res.isSuccessful) {
            res.body()?.let {
                emit(it)
            }
        }
    }

    /**
     * 공지사항 조회
     */
    suspend fun getNotice(): Flow<NoticeData> = flow {
        val res = apis.getNotice()
        if (res.isSuccessful) {
            res.body()?.let {
                emit(it)
            }
        }
    }




    /**
     * 알림 조회
     */
    suspend fun getNotification(): Flow<NotificationData> = flow {
        val res = apis.getNotification()
        if (res.isSuccessful) {
            res.body()?.let {
                emit(it)
            }
        }
    }

}