package com.awab.links.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.awab.links.utils.QRCodeGen
import com.awab.links.databinding.CreateQrcodeFragmentBinding

class CreateQRCodeFragment : Fragment() {

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

        binding.btnCreate.setOnClickListener {
            val text = binding.etText.text.toString()
            if (text.isNotBlank()) {
                val qrCodeBitmap = QRCodeGen().encodeAsBitmap(text, 200, 200)

                if (qrCodeBitmap != null) {
                    binding.ivQrCode.setImageBitmap(qrCodeBitmap)
                } else
                    Toast.makeText(requireContext(), "can't create QR code!...", Toast.LENGTH_SHORT).show()
            }else
                Toast.makeText(requireContext(), "can't create QR code!...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}