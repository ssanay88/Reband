package com.project.reband.network.recruitment

import com.project.reband.api.CommonApi
import com.project.reband.data.recruitment.HiringData
import com.project.reband.data.recruitment.MyApplyEntry
import com.project.reband.data.recruitment.RecruitmentDetailData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecruitmentService {

    @GET(CommonApi.Common.RECRUITMENT_LIST)
    suspend fun getRecruitmentList(): Response<HiringData.RecruitmentListData>

    @GET(CommonApi.Common.RECRUITMENT_DETAIL)
    suspend fun getRecruitmentDetail(
        @Path("recruitmentNo") recruitmentNo: Int
    ): Response<RecruitmentDetailData>

    @POST(CommonApi.Common.RECRUITMENT_APPLY)
    suspend fun applyRecruitment(
        @Body body: Map<String, Int>
    )

    @POST(CommonApi.Common.RECRUITMENT_CREATE)
    suspend fun createRecruitment(
        @Body body: Map<String, Any>
    )

    @GET(CommonApi.Common.RECRUITMENT_APPLY_LIST)
    suspend fun getMyApplyList(): Response<MyApplyEntry.MyApplyData>
}