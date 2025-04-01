package com.project.reband.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.project.reband.R
import com.project.reband.adapter.LocationSpinnerAdapter
import com.project.reband.databinding.FragmentBandRegisterBinding
import com.project.reband.viewmodel.BandRegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BandRegisterFragment : Fragment() {

    private val binding: FragmentBandRegisterBinding by lazy {
        FragmentBandRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<BandRegisterViewModel>()

    private var bandName: String = ""
    private var bandImage: Uri? = null
    private var locationFirstDepth: String = ""
    private var locationSecondDepth: String = ""
    private var introduce: String = ""
    private var mediaUrl: String = ""

    val pickImageOverSdk13 = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri ->
        if (imageUri != null) {
            setImageOnBandThumbnailBtn(imageUri)
        } else {
            Log.d("tngur", "[Image load fail over Sdk 13] - SDK : ${Build.VERSION.SDK_INT}")
        }
    }

    val pickImageUnderSdk13 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            imageUri?.let {
                setImageOnBandThumbnailBtn(imageUri)
            }
        } else {
            Log.d("tngur", "[Image load fail under Sdk 13] - SDK : ${Build.VERSION.SDK_INT}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.apply {

            regionList.apply {
                val locationList = resources.getStringArray(R.array.LocationList)

                val mAdapter = LocationSpinnerAdapter(
                    requireContext(),
                    R.layout.item_spinner_location,
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

            btnCancel.setOnClickListener {
                back()
            }

            btnRegister.setOnClickListener {
                createBand()
            }

            btnBandThumbnail.setOnClickListener {
                getBandImage()
            }
        }

        return binding.root
    }

    private fun setImageOnBandThumbnailBtn(imageUri: Uri) {
        Glide.with(this)
            .load(imageUri)
            .transform(CenterCrop())
            .into(binding.btnBandThumbnail)
    }

    private fun back() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun createBand() {

    }

    private fun getBandImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13 이상
            pickImageOverSdk13.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } else {
            // Android 13 이하
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            pickImageUnderSdk13.launch(intent)
        }
    }

}