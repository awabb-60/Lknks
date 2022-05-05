package com.awab.links.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.awab.links.R
import com.awab.links.databinding.LinkShortenerFragmentBinding
import com.awab.links.databinding.QrcodeFragmentBinding

class QRCodeFragment: Fragment(){

    private var _binding: QrcodeFragmentBinding? = null
    private val binding: QrcodeFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = QrcodeFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scan.setOnClickListener {
        Navigation.findNavController(it).navigate(R.id.action_QRCodeFragment_to_scanQRCodeFragment)
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}