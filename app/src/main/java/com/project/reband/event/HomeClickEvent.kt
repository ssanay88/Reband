package com.project.reband.event

sealed class HomeClickEvent() {
    object SearchNewMemberAddBtnClick : HomeClickEvent()

    object ShowMoreRecruitingBandBtnClick : HomeClickEvent()

    object CreateNewBandBtnClick : HomeClickEvent()

    object WaitingNewBandBtnClick : HomeClickEvent()
}