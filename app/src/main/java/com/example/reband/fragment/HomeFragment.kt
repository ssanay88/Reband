package com.example.reband.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.reband.R
import com.example.reband.RecruitingBandItemDecoration
import com.example.reband.adapter.RecruitingBandAdapter
import com.example.reband.data.RecruitingBandData
import com.example.reband.databinding.ActivityMainBinding
import com.example.reband.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    private var isLoggedIn = false
    private val homeBinding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initBinding()

        val testImage = "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzExMTFfMjM1%2FMDAxNjk5NzA2MzU1NTU2.atMcTOwNM5QhMXIZ0VU3W9Cvoy3TYrjv6r9fbpGPJ3Eg.LX6IsacnCtuIu_cUdo8c_3v0KNYxdd194-b8vcHfFikg.PNG.gabipet%2F%25B8%25BB%25C6%25BC%25C1%25EE%2528%25B8%25F4%25C6%25BC%25C1%25EE%2529_%25BC%25BA%25B0%25DD%252C_%25C6%25AF%25C2%25A1%252C_%25BC%25F6%25B8%25ED%25BF%25A1_%25B4%25EB%25C7%25D1_%25B1%25E2%25BA%25BB_%25C1%25A4%25BA%25B8002.png&type=a340"

        val testList = listOf(
            RecruitingBandData("활화산", testImage, "기타", "#서울"),
            RecruitingBandData("붉은주먹", testImage, "보컬", "#직장인"),
            RecruitingBandData("로랜드고릴라", testImage, "드럼", "#락 #J-POP"),
            RecruitingBandData("작은 밴드", testImage, "베이스", "#공연"),
        )

        val recruitingBandAdapter = RecruitingBandAdapter()

        homeBinding.rvRecruitingNewMember.apply {
            adapter = recruitingBandAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            if (itemDecorationCount == 0) {
                addItemDecoration(RecruitingBandItemDecoration())
            }
        }

        recruitingBandAdapter.submitList(testList)

        return homeBinding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun initBinding() {

    }
}