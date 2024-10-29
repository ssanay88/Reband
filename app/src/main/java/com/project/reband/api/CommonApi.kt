package com.project.reband.api

class CommonApi {

    object Common {
        /**
         * 규정 API
         */
        const val TERMS_OF_SERVICE = "/etc/terms-of-service"    // 약관 조회
        const val NOTICE = "/etc/notice"    // 공지사항 조회
        const val LOCATION = "/etc/location"    // 지역 조회
        const val INSTRUMENT = "/etc/instrument"    // 악기 목록 조회
        const val HASHTAG = "/etc/hashtag"    // 해시태그 조회

        /**
         * 인재풀 API
         */
        const val TALENTPOOL = "/talentPool"    // 인재풀 관련
        const val TALENTPOOL_OFFER = "/talentPool/offer"    // 밴드 가입 관련
        const val TALENPOOL_DETAIL = "/talentPool/detail"    // 인재풀 상세 조회

        /**
         * 회원 API
         */
        const val PROFILE = "/member/profile"    // 프로필 조회
        const val NICKNAME = "/member/nickname"    // 닉네임 중복 체크
        const val WITHDRAW_MEMBER = "/member"    // 회원 탈퇴

        /**
         * 밴드 API
         */
        const val BAND_UPDATE = "/band/update"    // 밴드 수정
        const val BAND_CREATE = "/band/create"    // 밴드 신규 생성
        const val BAND_MODIFY_STATUS = "/band/modify/status"    // 밴드 상태 변경
        const val BAND_MODIFY_MEMBER_STATUS = "/band/modify/member/status"    // 밴드 멤버 상태 변경
        const val BAND_LEAVE = "/band/leave"    // 밴드 탈퇴
        const val BAND_INSTRUMENT_MEMBER = "/band/instrument/member"    // 밴드 악기별 멤버 조회
        const val BAND_DETAIL = "/band/detail"    // 밴드 조회

        /**
         * 공고 API
         */
        const val RECRUITMENT_CREATE = "/recruitment/create"    // 공고 등록
        const val RECRUITMENT_APPLY = "/recruitment/apply"    // 공고 지원
        const val RECRUITMENT_MODIFY_STATUS = "/recruitment/modify/status"    // 공고 상태 변경
        const val RECRUITMENT_MODIFY_APPLY_STATUS = "/recruitment/modify/apply/status"    // 공고 상태 변경
        const val RECRUITMENT_LIST = "/recruitment/list"    // 공고 목록 조회
        const val RECRUITMENT_DETAIL = "/recruitment/detail"    // 공고 상세 조회
        const val RECRUITMENT_APPLY_LIST = "/recruitment/apply/list"    // 공고 지원 현황 조회

        /**
         * 알림 API
         */
        const val NOTIFICATION = "/notification"    // 알림 조회
    }

}