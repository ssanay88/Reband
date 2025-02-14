package com.project.reband.network.etc

import com.project.reband.api.CommonApi
import com.project.reband.data.etc.HashTagData
import com.project.reband.data.etc.InstrumentData
import com.project.reband.data.etc.NoticeData
import com.project.reband.data.etc.NotificationData
import com.project.reband.data.etc.TermsOfServiceData
import com.project.reband.data.user.UserInfoData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EtcService {

    @GET(CommonApi.Common.OAUTH_KAKAO)
    suspend fun getUserInfoKakao(
        @Query("accessToken") accessToken: String,
        @Query("refreshToken") refreshToken: String
    ): Response<UserInfoData>

    @GET(CommonApi.Common.OAUTH_NAVER)
    suspend fun getUserInfoNaver(
        @Query("accessToken") accessToken: String,
        @Query("refreshToken") refreshToken: String
    ): Response<UserInfoData>


    @GET(CommonApi.Common.TERMS_OF_SERVICE)
    suspend fun getTermsOfService(): Response<TermsOfServiceData>

    @GET(CommonApi.Common.NOTICE)
    suspend fun getNotice(): Response<NoticeData>

    @GET(CommonApi.Common.NOTIFICATION)
    suspend fun getNotification(): Response<NotificationData>

}