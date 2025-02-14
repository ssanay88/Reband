package com.project.reband.network.talentpool

import com.project.reband.api.CommonApi
import com.project.reband.data.talentpool.PoolData
import com.project.reband.data.talentpool.TalentPoolDetailData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TalentPoolService {

    @GET(CommonApi.Common.TALENTPOOL)
    suspend fun getTalentPool(): Response<PoolData.TalentPoolData>

    @GET(CommonApi.Common.TALENPOOL_DETAIL)
    suspend fun getTalentPoolDetail(
        @Query("no") poolNo: Int
    ): Response<TalentPoolDetailData>

    @POST(CommonApi.Common.TALENTPOOL_OFFER)
    suspend fun suggestJoinToBand(
        @Body bandNo: Int
    )

}