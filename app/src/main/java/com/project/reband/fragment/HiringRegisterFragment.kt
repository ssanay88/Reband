package com.project.reband.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.reband.databinding.FragmentHiringRegisterBinding
import com.project.reband.viewmodel.HiringRegisterViewModel
import kotlinx.coroutines.launch

class HiringRegisterFragment : Fragment() {

    private val binding : FragmentHiringRegisterBinding by lazy {
        FragmentHiringRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: HiringRegisterViewModel by viewModels()

    private var instrument: String = ""
    private var minExperience: Int = 0
    private var maxExperience: Int = 0
    private var gender: String = ""
    private var age: Int = 0
    private var content: String = ""
    private var contractUrl: String = ""
    private var hashTagList: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding?.apply {
            btnCancel.setOnClickListener {
                back()
            }

            btnRegister.setOnClickListener {
                registerProfile()
            }
        }

        return binding.root
    }


    private fun registerProfile() {
        lifecycleScope.launch {
            // viewModel.registerProfile(instrument, minExperience, maxExperience, gender, age, content, contractUrl, hashTagList)
            back()
        }

    }

    private fun back() {
        requireActivity().supportFragmentManager.popBackStack()
    }

}