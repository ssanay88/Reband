package com.example.reband.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reband.adapter.HiringFragmentAdapter
import com.example.reband.data.RecruitingBandData
import com.example.reband.databinding.FragmentHiringBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HiringFragment : Fragment() {

    private val hiringBinding: FragmentHiringBinding by lazy {
        FragmentHiringBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val testImage = "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzExMTFfMjM1%2FMDAxNjk5NzA2MzU1NTU2.atMcTOwNM5QhMXIZ0VU3W9Cvoy3TYrjv6r9fbpGPJ3Eg.LX6IsacnCtuIu_cUdo8c_3v0KNYxdd194-b8vcHfFikg.PNG.gabipet%2F%25B8%25BB%25C6%25BC%25C1%25EE%2528%25B8%25F4%25C6%25BC%25C1%25EE%2529_%25BC%25BA%25B0%25DD%252C_%25C6%25AF%25C2%25A1%252C_%25BC%25F6%25B8%25ED%25BF%25A1_%25B4%25EB%25C7%25D1_%25B1%25E2%25BA%25BB_%25C1%25A4%25BA%25B8002.png&type=a340"

        val testList = listOf(
            RecruitingBandData("활화산", testImage, "기타", "#서울", "안녕하세요 저희 밴드는 활화산 그 자체로 열정넘치는 밴드입니다. 직장인으로 이루어졌으면 매번 뒷풀이가 있습니다. 많은 신청 바랍니다."),
            RecruitingBandData("붉은주먹", testImage, "보컬", "#직장인", "주먹 하나, 주먹 둘, 주먹 셋 주먹을 지르면 용기가 생겨나서 합주에 도움을 줍니다. 모두 많은 지원 바랍니다."),
            RecruitingBandData("로랜드고릴라", testImage, "드럼", "#락 #J-POP", "릴라 릴라 고릴라 라라라라 고릴라 고릴 고릴 고릴라 우다다닫 고릴라 우리집 고릴라 릴라 릴라"),
            RecruitingBandData("작은 밴드", testImage, "베이스", "#공연" ,"베이스 구합 베이스 구함 어디 베이스 없나 아쉽군 베이스 찾아요"),
            RecruitingBandData("술고래", testImage, "기타", "#뒷풀이", "술고래입니다. 저희는 합주보단 뒷풀이가 중요합니다~~ 다들 모여라 부어라 마셔라~~ 놀자~"),
            RecruitingBandData("빠라빠빠빠", testImage, "드럼", "#신난다 #부산", "나는 개똥벌레 친구가 없네~ 저기 개똥무덤이 내 집인걸~ 가슴을 내밀어도 친구가 없네"),
            RecruitingBandData("서울경찰청", testImage, "보컬", "#미란다원칙", "당신을 묵비권을 행사할 수 있고, 지금 하는 모든 발언은 법정에서 불리한 증언으로 사용될 수 있습니다.")
        )

        val hiringBandAdapter = HiringFragmentAdapter()

        hiringBinding.rvHiringFragment.apply {
            adapter = hiringBandAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        hiringBandAdapter.submitList(testList)

        return hiringBinding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HiringFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}