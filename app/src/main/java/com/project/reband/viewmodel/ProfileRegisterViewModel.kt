package com.project.reband.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.reband.data.ProfileInfo
import com.project.reband.network.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileRegisterViewModel: ViewModel() {
    private val repository = ProfileRepository()

    private val _usingNickname = MutableStateFlow<Boolean>(false)
    val usingNickname = _usingNickname.asStateFlow()

    fun createRandomNickname() : String{
        val randomNick = mutableListOf<String>("멍멍야옹","뻔뻔꿀렁","번쩍깜깜","따뜻시원","징글징글","옹기종기","왁자지껄","부산대구","삼성현대")
        val randomNum = (1000..9999).random()

        return randomNick.random() + randomNum.toString()

    }

    fun checkNickname(nickname: String) {
        viewModelScope.launch {
            repository.getNickname(nickname).collectLatest {
                if (it.code == "200") {
                    _usingNickname.emit(true)
                } else {
                    _usingNickname.emit(false)
                }
            }
        }

    }

    fun registerProfile(
        nickname: String,
        instrument: String,
        experience: Int,
        firstDepth: String,
        secondDepth: String,
        gender: String,
        age: Int,
        chatUrl: String,
        mediaUrl: String,
        introduce: String
    ) {
        viewModelScope.launch {
            val profile = ProfileInfo(nickname, instrument, experience, firstDepth, secondDepth, gender, age, chatUrl, mediaUrl, introduce)
            repository.registerProfile(profile)
        }

    }

}