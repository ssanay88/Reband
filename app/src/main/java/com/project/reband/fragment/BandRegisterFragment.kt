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
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
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