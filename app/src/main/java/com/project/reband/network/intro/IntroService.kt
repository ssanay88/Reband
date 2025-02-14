package com.project.reband.network.intro

import com.project.reband.api.CommonApi
import com.project.reband.data.etc.HashTagData
import com.project.reband.data.etc.InstrumentData
import com.project.reband.data.etc.LocationFirstDepthData
import com.project.reband.data.etc.LocationSecondDepthData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IntroService {

    @GET(CommonApi.Common.LOCATION)
    suspend fun getDepth1Location(
        @Query("type") type: Int
    ): Response<LocationFirstDepthData>

    @GET(CommonApi.Common.LOCATION)
    suspend fun getDepth2Location(
        @Query("type") type: Int,
        @Query("firstDepthName") firstDepthName: String = "",
    ): Response<LocationSecondDepthData>

    @GET(CommonApi.Common.HASHTAG)
    suspend fun getHashTag(): Response<HashTagData>

    @GET(CommonApi.Common.INSTRUMENT)
    suspend fun getInstrument(): Response<InstrumentData>

}