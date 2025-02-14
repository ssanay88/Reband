package com.project.reband.network.recruitment

import android.util.Log
import com.project.reband.RetrofitService
import com.project.reband.data.recruitment.HiringData
import com.project.reband.data.recruitment.MyApplyEntry
import com.project.reband.data.recruitment.RecruitmentDetailData
import com.project.reband.network.talentpool.TalentPoolService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.Body

class RecruitmentRepository {
    private val recruitmentRepository = RetrofitService.retrofit.create(RecruitmentService::class.java)

    /**
     * 전체 공고 목록 조회
     */
    suspend fun getRecruitmentList(): Flow<HiringData.RecruitmentList> = flow {
        val res = recruitmentRepository.getRecruitmentList()
        if (res.isSuccessful) {
            res.body()?.let {
                emit(it.data)
            }
        }
    }

    /**
     * 상세 모집 공고 조회
     */
    suspend fun getRecruitmentDetail(recruitmentNo: Int): Flow<RecruitmentDetailData.RecruitmentDetail> = flow {
        val res = recruitmentRepository.getRecruitmentDetail(recruitmentNo)
        if (res.isSuccessful) {
            res.body()?.let {
                emit(it.data)
                Log.d("tngur", "success : ${it.data}")
            }
        } else {
            Log.d("tngur", "fail : ${res.message()}")
        }
    }

    /**
     * 밴드 지원하기
     */
    suspend fun applyRecruitment(recruitmentNo: Int): Flow<Unit> = flow {
        recruitmentRepository.applyRecruitment(mapOf("recruitmentNo" to recruitmentNo))
    }

    /**
     * 공고 등록
     */
    suspend fun createRecruitment(body: Body): Flow<Unit> = flow {
        // recruitmentRepository.createRecruitment(body)
    }

    /**
     * 지원 현황 조회
     */
    suspend fun getMyApplyList() : Flow<MyApplyEntry.MyApplyList> = flow {
        val res = recruitmentRepository.getMyApplyList()
        if (res.isSuccessful) {
            res.body()?.let {
                emit(it.data)
            }
        }

    }

}