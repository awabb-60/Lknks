package com.awab.links.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.awab.links.MainViewModel
import com.awab.links.databinding.CreateQrcodeFragmentBinding


class CreateQRCodeFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var _binding: CreateQrcodeFragmentBinding? = null
    private val binding: CreateQrcodeFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreateQrcodeFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireContext() as ViewModelStoreOwner)[MainViewModel::class.java]

        viewModel.qrCodeBitmap.observe(viewLifecycleOwner) {
            binding.ivQrCode.setImageBitmap(it)

            // hiding the keyboard after success
            val inputMethod =
                requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethod.hideSoftInputFromWindow(binding.etText.windowToken, 0)
        }

        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            message?.let { Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show() }
        }

        binding.btnCreate.setOnClickListener {
            viewModel.createQRCode(binding.etText.text.toString())
        }

        binding.ivSave.setOnClickListener {
            viewModel.saveCurrentQRCode(requireContext(), requireActivity())
        }

        binding.ivShare.setOnClickListener {
            val intent = viewModel.getShareTextIntent(requireContext())
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}