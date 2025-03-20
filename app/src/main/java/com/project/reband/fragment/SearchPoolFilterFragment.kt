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
import com.project.reband.R
import com.project.reband.adapter.LocationSpinnerAdapter
import com.project.reband.data.etc.LocationSecondDepth
import com.project.reband.databinding.FragmentSearchBandFilterBinding
import com.project.reband.databinding.FragmentSearchPoolFilterBinding

class SearchPoolFilterFragment : Fragment() {

    private val binding: FragmentSearchPoolFilterBinding by lazy {
        FragmentSearchPoolFilterBinding.inflate(layoutInflater)
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

                val location1List = resources.getStringArray(R.array.LocationList)

                val mAdapter = LocationSpinnerAdapter(
                    requireContext(),
                    R.layout.item_spinner_location,
                    location1List
                )
                adapter = mAdapter

                onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?, view: View?, position: Int, id: Long
                        ) {
                            val location2List = when (location1List[position]) {
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
                                val detailAdapter = LocationSpinnerAdapter(
                                    parent!!.context,
                                    R.layout.item_spinner_location,
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
                        setBackgroundResource(0)
                        setTextColor(resources.getColor(R.color.grayText))
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
                        setBackgroundResource(0)
                        setTextColor(resources.getColor(R.color.grayText))
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
                        setBackgroundResource(0)
                        setTextColor(resources.getColor(R.color.grayText))
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
                        setBackgroundResource(0)
                        setTextColor(resources.getColor(R.color.grayText))
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
                        setBackgroundResource(0)
                        setTextColor(resources.getColor(R.color.grayText))
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
                        setBackgroundResource(0)
                        setTextColor(resources.getColor(R.color.grayText))
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
                        setBackgroundResource(0)
                        setTextColor(resources.getColor(R.color.grayText))
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
                        setBackgroundResource(0)
                        setTextColor(resources.getColor(R.color.grayText))
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
                        setBackgroundResource(0)
                        setTextColor(resources.getColor(R.color.grayText))
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
                        setBackgroundResource(0)
                        setTextColor(resources.getColor(R.color.grayText))
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
                        setBackgroundResource(0)
                        setTextColor(resources.getColor(R.color.grayText))
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
                        setBackgroundResource(0)
                        setTextColor(resources.getColor(R.color.grayText))
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
                        setBackgroundResource(0)
                        setTextColor(resources.getColor(R.color.grayText))
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