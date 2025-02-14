package com.project.reband.network.profile

import android.util.Log
import com.project.reband.RetrofitService
import com.project.reband.data.ProfileInfo
import com.project.reband.data.member.MemberData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class ProfileRepository {

    private val apis = RetrofitService.retrofit.create(ProfileService::class.java)

    /**
     * 닉네임 중복 여부
     */
    suspend fun getNickname(nickname: String): Flow<MemberData> = flow {
        val res = apis.getNickname(nickname)
        if (res.isSuccessful) {
            res.body()?.let {
                emit(it)
            }
        }
    }

    /**
     * 프로필 등록
     */
    suspend fun registerProfile(profileInfo: ProfileInfo) {
        val res = apis.registerProfile(profileInfo)
        if (res.isSuccessful) {
            Log.d("tngur", "registerProfile is successful")
        }
    }
}