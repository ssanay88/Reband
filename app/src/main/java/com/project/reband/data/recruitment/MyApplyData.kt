package com.project.reband.data.recruitment

sealed interface MyApplyEntry {

    object AppliedTitle : MyApplyEntry

    object SuggestedTitle : MyApplyEntry

    data class MyApplyData(
        val code: String,
        val message: String,
        val data: MyApplyList
    )

    data class MyApplyList(
        val myApplyList: MutableList<MyApply>
    )

    data class MyApply(
        val recruitmentNo: Int,
        val bandNo: Int,
        val bandName: String,
        val applyState: String,
        val instrument: Int,
        val ageGroup: Int
    ) : MyApplyEntry



}


