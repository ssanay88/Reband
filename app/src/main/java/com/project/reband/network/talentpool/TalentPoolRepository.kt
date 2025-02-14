package com.project.reband.network.talentpool

import com.project.reband.RetrofitService
import com.project.reband.data.talentpool.PoolData
import com.project.reband.data.talentpool.TalentPoolDetailData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class TalentPoolRepository() {
    private val apis = RetrofitService.retrofit.create(TalentPoolService::class.java)

    /**
     * 인재풀 정보 조회
     */
    suspend fun getTalentPool(): Flow<PoolData.TalentPoolData> = flow {
        val res = apis.getTalentPool()
        if (res.isSuccessful) {
            res.body()?.let {
                emit(it)
            }
        }
    }

    /**
     * 인재풀 상세 정보 조회
     */
    suspend fun getTalentPoolDetail(poolNo: Int): Flow<TalentPoolDetailData.TalentPoolDetail> = flow {
        val res = apis.getTalentPoolDetail(poolNo)
        if (res.isSuccessful) {
            res.body()?.let {
                emit(it.data)
            }
        }
    }

    /**
     * 인재풀 밴드 가입 제안
     */
    suspend fun suggestJoinToBand(poolNo: Int): Flow<Any> = flow {
        apis.suggestJoinToBand(poolNo)
    }


}