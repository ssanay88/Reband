package com.project.reband.network.band

import com.project.reband.api.CommonApi
import com.project.reband.data.band.BandCreateData
import com.project.reband.data.band.BandDetailData
import com.project.reband.data.band.BandMemberModifyData
import com.project.reband.data.recruitment.HiringData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface BandService {

    /**
     * 밴드 신규 생성
     */
    @POST(CommonApi.Common.BAND_CREATE)
    suspend fun createBand(
        @Body bandCreateData: BandCreateData
    )

    /**
     * 밴드 조회
     */
    @GET(CommonApi.Common.BAND_DETAIL)
    suspend fun getBandDetail(
        @Query("bandNo") bandNo: Int
    ): Response<BandDetailData>

    /**
     * 밴드 멤버 상태 변경
     */
    @PATCH(CommonApi.Common.BAND_MODIFY_MEMBER_STATUS)
    suspend fun modifyMemberStatus(
        @Body bandMemberModifyData: BandMemberModifyData
    )


}