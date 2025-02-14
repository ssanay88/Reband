package com.project.reband.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.reband.GlobalApplication
import com.project.reband.R
import com.project.reband.data.etc.HashTagData
import com.project.reband.databinding.FragmentMyPageBinding
import com.project.reband.databinding.FragmentProfileRegisterBinding
import com.project.reband.utils.Util
import com.project.reband.viewmodel.ProfileRegisterViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileRegisterFragment(): Fragment() {

    private val binding: FragmentProfileRegisterBinding by lazy {
        FragmentProfileRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: ProfileRegisterViewModel by viewModels()
    private val dataStore = GlobalApplication.getInstance().getDataStore()

    private var nickname: String = ""
    private var instrument: String = ""
    private var experience: Int = 0
    private var firstDepth: String = ""
    private var secondDepth: String = ""
    private var gender: String = "MEN"
    private var age: Int = 0
    private var chatUrl: String = ""
    private var mediaUrl: String = ""
    private var introduce: String = ""
    private val optionList = mutableListOf<String>()
    private var llExtraOptionWidth = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        binding.etNickname.setText(viewModel.createRandomNickname())

        lifecycleScope.launch {
            viewModel.usingNickname.collectLatest {
                binding.tvCheckNicknameMessage.apply {
                    if (it) {
                        text = "사용 가능한 닉네임입니다."
                        setTextColor(resources.getColor(R.color.blackText))
                    } else {
                        text = "이미 사용중인 닉네임입니다."
                        setTextColor(resources.getColor(R.color.red))
                    }
                }
            }
        }

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding.apply {
            btnCancel.setOnClickListener {
                back()
            }

            btnRegister.setOnClickListener {
                registerProfile()
            }

            nickname = etNickname.text.toString()

            etNickname.apply {
                addTextChangedListener {
                    viewModel.checkNickname(text.toString())
                }
            }

            regionList.apply {
                val location1List = resources.getStringArray(R.array.LocationList)

                val mAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    location1List
                )
                adapter = mAdapter

                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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

            positionRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
                when(i) {
                    R.id.cb_drum_position -> {
                        instrument = "DRUM"
                    }
                    R.id.cb_bass_position -> {
                        instrument = "BASS"
                    }
                    R.id.cb_vocal_position -> {
                        instrument = "VOCAL"
                    }
                    R.id.cb_guitar_position -> {
                        instrument = "GUITAR"
                    }
                    R.id.cb_keyboard_position -> {
                        instrument = "KEYBOARD"
                    }
                }
            }

            rangeSliderCareer.apply {
                valueFrom = 0f
                valueTo = 30f
                stepSize = 1f
                value = 0f
                addOnChangeListener { slider, value, fromUser ->
                    experience = slider.value.toInt()

                }
            }

            tvMale.setOnClickListener {
                gender = "MAN"
                tvMale.apply {
                    setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                    setTextColor(resources.getColor(R.color.white))
                }
                tvFemale.apply {
                    setBackgroundResource(0)
                    setTextColor(resources.getColor(R.color.grayText))
                }
            }

            tvFemale.setOnClickListener {
                gender = "WOMAN"
                tvMale.apply {
                    setBackgroundResource(0)
                    setTextColor(resources.getColor(R.color.grayText))
                }
                tvFemale.apply {
                    setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                    setTextColor(resources.getColor(R.color.white))
                }
            }

            tvAge10.setOnClickListener {
                ageBtnClickListener(binding.tvAge10)
            }

            tvAge20.setOnClickListener {
                ageBtnClickListener(binding.tvAge20)
            }

            tvAge30.setOnClickListener {
                ageBtnClickListener(binding.tvAge30)
            }

            tvAge40.setOnClickListener {
                ageBtnClickListener(binding.tvAge40)
            }

            tvAge50.setOnClickListener {
                ageBtnClickListener(binding.tvAge50)
            }

            tvAge60More.setOnClickListener {
                ageBtnClickListener(binding.tvAge60More)
            }

            llExtraOption.apply {
                viewTreeObserver.addOnGlobalLayoutListener {
                llExtraOptionWidth = measuredWidth
                }
                lifecycleScope.launch {
                    val hashTagList : MutableList<HashTagData.HashTagList.HashTag>
                    = Gson().fromJson(dataStore.hashTagList.first(), object : TypeToken<MutableList<HashTagData.HashTagList.HashTag?>?>() {}.type)
                    setOptionLinearLayout(hashTagList)
                }
            }

            chatUrl = etOpenkakaotalkUrl.text.toString()

            introduce = etSelfIntro.text.toString()
        }



        return binding.root
    }

    private fun registerProfile() {
        lifecycleScope.launch {
            viewModel.registerProfile(nickname, instrument, experience, firstDepth, secondDepth, gender, age, chatUrl, mediaUrl, introduce)
            back()
        }

    }

    private fun back() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun ageBtnClickListener(ageBtn : TextView) {
        ageBtn.apply {
            clearAllAgeBtn()
            setBackgroundResource(R.drawable.toggle_selected_btn_bg)
            setTextColor(resources.getColor(R.color.white))
            age = text.toString().substring(0,2).toInt()
        }
    }

    private fun clearAllAgeBtn() {
        listOf(
            binding.tvAge10,
            binding.tvAge20,
            binding.tvAge30,
            binding.tvAge40,
            binding.tvAge50,
            binding.tvAge60More
        ).forEach {
            it.apply {
                setBackgroundResource(R.drawable.round_btn_bg)
                setTextColor(resources.getColor(R.color.blackText))
            }
        }
    }

    private fun makeNewLinearLayout() {

    }

    // 전체 레이아웃을 추가
    fun setOptionLinearLayout(hashTagList : MutableList<HashTagData.HashTagList.HashTag>) {
        binding.llExtraOption.removeAllViews()

        var newLinearLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        var textViewWidthSum = 0

        hashTagList.forEach { option ->
            // 새로운 텍스트뷰 생성
            val textView = makeOptionTextView(option.name)
            textView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            // 텍스트뷰의 양쪽 마진값도 계산
            val textViewMargin = (textView.layoutParams as ViewGroup.MarginLayoutParams).let {
                it.leftMargin + it.rightMargin
            }
            // 텍스트뷰의 총 길이
            val textViewWidth = textView.measuredWidth + textViewMargin

            if (textViewWidthSum + textViewWidth > llExtraOptionWidth) {
                // 부모 레이아웃에 생성된 라인 추가
                binding.llExtraOption.addView(newLinearLayout)

                // 새로운 라인 생성
                newLinearLayout = LinearLayout(requireContext()).apply {
                    orientation = LinearLayout.HORIZONTAL
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                textViewWidthSum = 0
            }
            newLinearLayout.addView(textView)
            textViewWidthSum += textViewWidth
        }

        binding.llExtraOption.addView(newLinearLayout)
    }

    private fun makeOptionTextView(text: String): TextView {
        val optionTextView = TextView(requireContext()).apply {
            setText(text)
            setBackgroundResource(R.drawable.round_btn_bg)
            setTextAppearance(R.style.NoneSelectedRoundButtonTextStyle)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                val horizontalMarginDp = 4f
                val verticalMarginDp = 3f
                setMargins(
                    Util.dpToPx(context, horizontalMarginDp),
                    Util.dpToPx(context, verticalMarginDp),
                    Util.dpToPx(context, horizontalMarginDp),
                    Util.dpToPx(context, verticalMarginDp)
                )
            }

            val horizontalPaddingDp = 8f
            val verticalPaddingDp = 6f
            setPadding(
                Util.dpToPx(context, horizontalPaddingDp),
                Util.dpToPx(context, verticalPaddingDp),
                Util.dpToPx(context, horizontalPaddingDp),
                Util.dpToPx(context, verticalPaddingDp)
            )

            setOnClickListener {
                var selected = optionList.contains(text)
                if (!selected) {
                    setBackgroundResource(R.drawable.toggle_selected_btn_bg)
                    setTextAppearance(R.style.SelectedRoundButtonTextStyle)
                    optionList.add(text)
                } else {
                    setBackgroundResource(R.drawable.round_btn_bg)
                    setTextAppearance(R.style.NoneSelectedRoundButtonTextStyle)
                    optionList.remove(text)
                }
            }
        }

        return optionTextView
    }

}