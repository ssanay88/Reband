package com.project.reband.network.profile

import com.project.reband.api.CommonApi
import com.project.reband.data.ProfileInfo
import com.project.reband.data.member.MemberData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProfileService {

    @GET(CommonApi.Common.NICKNAME)
    suspend fun getNickname(
        @Query("nickname") nickname: String
    ): Response<MemberData>

    @POST(CommonApi.Common.PROFILE)
    suspend fun registerProfile(
        @Body body: ProfileInfo
    ): Response<ProfileInfo>

}