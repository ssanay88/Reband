package com.project.reband.network.main

import com.project.reband.RetrofitService
import com.project.reband.data.etc.HashTagData
import com.project.reband.data.etc.InstrumentData
import com.project.reband.data.etc.LocationFirstDepthData
import com.project.reband.data.etc.LocationSecondDepthData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository {

    private val apis = RetrofitService.retrofit.create(MainService::class.java)

    suspend fun getDepth1Location(type: Int): Flow<LocationFirstDepthData> = flow {
        val res = apis.getDepth1Location(type)
        if (res.isSuccessful) {
            res.body()?.let {
                emit(it)
            }
        }
    }

    suspend fun getDepth2Location(type: Int, firstDepthName: String = ""): Flow<LocationSecondDepthData> = flow {
        val res = apis.getDepth2Location(type, firstDepthName)
        if (res.isSuccessful) {
            res.body()?.let {
                emit(it)
            }
        }
    }

    /**
     * 악기 조회
     */
    suspend fun getInstrument(): Flow<InstrumentData.InstrumentList> = flow {
        val res = apis.getInstrument()
        if (res.isSuccessful) {
            res.body()?.let {
                emit(it.data)
            }
        }
    }

    /**
     * 해시태그 조회
     */
    suspend fun getHashTag(): Flow<HashTagData.HashTagList> = flow {
        val res = apis.getHashTag()
        if (res.isSuccessful) {
            res.body()?.let {
                emit(it.data)
            }
        }
    }
}