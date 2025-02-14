package com.project.reband.network.band

import com.project.reband.RetrofitService
import com.project.reband.data.band.BandCreateData
import com.project.reband.data.band.BandDetailData
import com.project.reband.data.band.BandMemberModifyData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BandRepository {

    private val apis = RetrofitService.retrofit.create(BandService::class.java)

    suspend fun createBand(bandCreateData: BandCreateData): Flow<Any> = flow {
        apis.createBand(bandCreateData)
    }

    suspend fun getBandDetail(bandNo: Int): Flow<BandDetailData> = flow {
        val res = apis.getBandDetail(bandNo)
        if (res.isSuccessful) {
            res.body()?.let {
                emit(it)
            }
        }
    }

    suspend fun modifyMemberStatus(bandMemberModifyData: BandMemberModifyData): Flow<Unit> = flow {
        apis.modifyMemberStatus(bandMemberModifyData)
    }
}