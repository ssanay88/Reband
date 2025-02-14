package com.project.reband.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.project.reband.GlobalApplication
import com.project.reband.R
import com.project.reband.data.etc.LocationSecondDepth
import com.project.reband.databinding.FragmentSearchBandFilterBinding
import kotlinx.coroutines.flow.map
import java.util.Objects

class SearchBandFilterFragment : Fragment() {

    private val binding: FragmentSearchBandFilterBinding by lazy {
        FragmentSearchBandFilterBinding.inflate(layoutInflater)
    }

    private var selectedSex: String = "Male"
    private var selected10: Boolean = false
    private var selected20: Boolean = false
    private var selected30: Boolean = false
    private var selected40: Boolean = false
    private var selected50: Boolean = false
    private var selected60: Boolean = false
    private var selectedMon: Boolean = false
    private var selectedTue: Boolean = false
    private var selectedWed: Boolean = false
    private var selectedThu: Boolean = false
    private var selectedFri: Boolean = false
    private var selectedSat: Boolean = false
    private var selectedSun: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.apply {
            tvMale.setOnClickListener {
                tvMale.apply {
                    selectedSex = "Male"
                    setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                    setTextColor(resources.getColor(R.color.white))
                }
                tvFemale.apply {
                    setBackgroundResource(0)
                    setTextColor(resources.getColor(R.color.grayText))
                }
            }
            tvFemale.setOnClickListener {
                tvMale.apply {
                    setBackgroundResource(0)
                    setTextColor(resources.getColor(R.color.grayText))
                }
                tvFemale.apply {
                    selectedSex = "Female"
                    setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                    setTextColor(resources.getColor(R.color.white))

                }
            }

            regionList.apply {
                val locationList = resources.getStringArray(R.array.LocationList)

                val mAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    locationList
                )
                adapter = mAdapter

                onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?, view: View?, position: Int, id: Long
                        ) {
                            val location2List = when (locationList[position]) {
                                "전라북도" -> resources.getStringArray(R.array.Jeollabukdo)
                                "제주특별자치도" -> resources.getStringArray(R.array.Jeju)
                                "대전광역시" -> resources.getStringArray(R.array.Daejeon)
                                "부산광역시" -> resources.getStringArray(R.array.Busan)
                                "강원도" -> resources.getStringArray(R.array.Gangwondo)
                                "전라남도" -> resources.getStringArray(R.array.Jeollanamdo)
                                "인천광역시" -> resources.getStringArray(R.array.Incheon)
                                "광주광역시" -> resources.getStringArray(R.array.Gwangju)
                                "울산광역시" -> resources.getStringArray(R.array.Ulsan)
                                "대구광역시" -> resources.getStringArray(R.array.Daegu)
                                "경상북도" -> resources.getStringArray(R.array.Gyeongsangbukdo)
                                "충청북도" -> resources.getStringArray(R.array.Chungcheongbukdo)
                                "경상남도" -> resources.getStringArray(R.array.Gyeongsangnamdo)
                                "충청남도" -> resources.getStringArray(R.array.Chungcheongnamdo)
                                "경기도" -> resources.getStringArray(R.array.Gyeonggido)
                                "서울특별시" -> resources.getStringArray(R.array.Seoul)
                                else -> resources.getStringArray(R.array.Sejong)
                            }

                            binding.detailRegionList.apply {
                                val detailAdapter = ArrayAdapter(
                                    parent!!.context,
                                    android.R.layout.simple_spinner_item,
                                    location2List
                                )
                                adapter = detailAdapter
                            }
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }

                    }
            }


            tvAge10.setOnClickListener {
                tvAge10.apply {
                    if (selected10) {
                        setBackgroundResource(R.drawable.round_btn_bg)
                        setTextColor(resources.getColor(R.color.blackText))
                        selected10 = false
                    } else {
                        setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                        setTextColor(resources.getColor(R.color.white))
                        selected10 = true
                    }
                }
            }

            tvAge20.setOnClickListener {
                tvAge20.apply {
                    if (selected20) {
                        setBackgroundResource(R.drawable.round_btn_bg)
                        setTextColor(resources.getColor(R.color.blackText))
                        selected20 = false
                    } else {
                        setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                        setTextColor(resources.getColor(R.color.white))
                        selected20 = true
                    }
                }
            }

            tvAge30.setOnClickListener {
                tvAge30.apply {
                    if (selected30) {
                        setBackgroundResource(R.drawable.round_btn_bg)
                        setTextColor(resources.getColor(R.color.blackText))
                        selected30 = false
                    } else {
                        setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                        setTextColor(resources.getColor(R.color.white))
                        selected30 = true
                    }
                }
            }

            tvAge40.setOnClickListener {
                tvAge40.apply {
                    if (selected40) {
                        setBackgroundResource(R.drawable.round_btn_bg)
                        setTextColor(resources.getColor(R.color.blackText))
                        selected40 = false
                    } else {
                        setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                        setTextColor(resources.getColor(R.color.white))
                        selected40 = true
                    }
                }
            }

            tvAge50.setOnClickListener {
                tvAge50.apply {
                    if (selected50) {
                        setBackgroundResource(R.drawable.round_btn_bg)
                        setTextColor(resources.getColor(R.color.blackText))
                        selected50 = false
                    } else {
                        setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                        setTextColor(resources.getColor(R.color.white))
                        selected50 = true
                    }
                }
            }

            tvAge60More.setOnClickListener {
                tvAge60More.apply {
                    if (selected60) {
                        setBackgroundResource(R.drawable.round_btn_bg)
                        setTextColor(resources.getColor(R.color.blackText))
                        selected60 = false
                    } else {
                        setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                        setTextColor(resources.getColor(R.color.white))
                        selected60 = true
                    }
                }
            }

            tvMonday.setOnClickListener {
                tvMonday.apply {
                    if (selectedMon) {
                        setBackgroundResource(R.drawable.round_btn_bg)
                        setTextColor(resources.getColor(R.color.blackText))
                        selectedMon = false
                    } else {
                        setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                        setTextColor(resources.getColor(R.color.white))
                        selectedMon = true
                    }
                }
            }

            tvTuesday.setOnClickListener {
                tvTuesday.apply {
                    if (selectedTue) {
                        setBackgroundResource(R.drawable.round_btn_bg)
                        setTextColor(resources.getColor(R.color.blackText))
                        selectedTue = false
                    } else {
                        setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                        setTextColor(resources.getColor(R.color.white))
                        selectedTue = true
                    }
                }
            }

            tvWednesday.setOnClickListener {
                tvWednesday.apply {
                    if (selectedWed) {
                        setBackgroundResource(R.drawable.round_btn_bg)
                        setTextColor(resources.getColor(R.color.blackText))
                        selectedWed = false
                    } else {
                        setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                        setTextColor(resources.getColor(R.color.white))
                        selectedWed = true
                    }
                }
            }

            tvThursday.setOnClickListener {
                tvThursday.apply {
                    if (selectedThu) {
                        setBackgroundResource(R.drawable.round_btn_bg)
                        setTextColor(resources.getColor(R.color.blackText))
                        selectedThu = false
                    } else {
                        setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                        setTextColor(resources.getColor(R.color.white))
                        selectedThu = true
                    }
                }
            }

            tvFriday.setOnClickListener {
                tvFriday.apply {
                    if (selectedFri) {
                        setBackgroundResource(R.drawable.round_btn_bg)
                        setTextColor(resources.getColor(R.color.blackText))
                        selectedFri = false
                    } else {
                        setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                        setTextColor(resources.getColor(R.color.white))
                        selectedFri = true
                    }
                }
            }

            tvSaturday.setOnClickListener {
                tvSaturday.apply {
                    if (selectedSat) {
                        setBackgroundResource(R.drawable.round_btn_bg)
                        setTextColor(resources.getColor(R.color.blackText))
                        selectedSat = false
                    } else {
                        setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                        setTextColor(resources.getColor(R.color.white))
                        selectedSat = true
                    }
                }
            }

            tvSunday.setOnClickListener {
                tvSunday.apply {
                    if (selectedSun) {
                        setBackgroundResource(R.drawable.round_btn_bg)
                        setTextColor(resources.getColor(R.color.blackText))
                        selectedSun = false
                    } else {
                        setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                        setTextColor(resources.getColor(R.color.white))
                        selectedSun = true
                    }
                }
            }


        }

        return binding.root
    }

}