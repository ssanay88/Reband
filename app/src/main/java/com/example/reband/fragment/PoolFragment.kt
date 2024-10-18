package com.example.reband.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reband.R
import com.example.reband.adapter.HiringFragmentAdapter
import com.example.reband.adapter.PoolFragmentAdapter
import com.example.reband.data.FindBandData
import com.example.reband.data.RecruitingBandData
import com.example.reband.databinding.FragmentPoolBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PoolFragment : Fragment() {

    private val poolBinding: FragmentPoolBinding by lazy {
        FragmentPoolBinding.inflate(layoutInflater)
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
            FindBandData(1,"베이스", testImage, 1, mutableListOf("서울","20대","남"), "안녕하세요 저희 밴드는 활화산 그 자체로 열정넘치는 밴드입니다. 직장인으로 이루어졌으면 매번 뒷풀이가 있습니다. 많은 신청 바랍니다."),
            FindBandData(2,"기타", testImage, 2, mutableListOf("경기","시흥"), "주먹 하나, 주먹 둘, 주먹 셋 주먹을 지르면 용기가 생겨나서 합주에 도움을 줍니다. 모두 많은 지원 바랍니다."),
            FindBandData(3,"드럼", testImage, 3, mutableListOf("서울","30대","직장인"), "릴라 릴라 고릴라 라라라라 고릴라 고릴 고릴 고릴라 우다다닫 고릴라 우리집 고릴라 릴라 릴라"),
            FindBandData(4,"드럼", testImage, 2, mutableListOf("ENTP","20대","여") ,"베이스 구합 베이스 구함 어디 베이스 없나 아쉽군 베이스 찾아요"),
            FindBandData(5,"보컬", testImage, 1, mutableListOf("부산","남"), "술고래입니다. 저희는 합주보단 뒷풀이가 중요합니다~~ 다들 모여라 부어라 마셔라~~ 놀자~"),
            FindBandData(6,"키보드", testImage, 2, mutableListOf("경주","활발","남"), "나는 개똥벌레 친구가 없네~ 저기 개똥무덤이 내 집인걸~ 가슴을 내밀어도 친구가 없네"),
            FindBandData(7, "기타", testImage, 1, mutableListOf("신림","홍대","남"), "당신을 묵비권을 행사할 수 있고, 지금 하는 모든 발언은 법정에서 불리한 증언으로 사용될 수 있습니다.")
        )

        val poolFragmentAdapter = PoolFragmentAdapter()

        poolBinding.rvPoolFragment.apply {
            adapter = poolFragmentAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        poolFragmentAdapter.submitList(testList)

        return poolBinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PoolFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PoolFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}