package com.awab.links.view

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.awab.links.R
import com.awab.links.databinding.ScanQrcodeFragmentBinding
import com.awab.links.utils.getShareTextIntent
import com.awab.links.utils.saveToClipBoard
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.ScanMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScanQRCodeFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner

    private var _binding: ScanQrcodeFragmentBinding? = null
    private val binding: ScanQrcodeFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScanQrcodeFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission()
        codeScanner = CodeScanner(requireContext(), binding.scanner)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            scanMode = ScanMode.CONTINUOUS

            isAutoFocusEnabled = true
            isFlashEnabled = false

            setDecodeCallback {
                CoroutineScope(Dispatchers.Main).launch { showScanResult(it.text) }
            }

            setErrorCallback {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(requireContext(), "error: failed to scan!", Toast.LENGTH_SHORT).show() }
            }
        }

        binding.scanner.setOnClickListener {
            checkPermission()
            codeScanner.startPreview()
        }

        binding.ibCopyText.setOnClickListener {
            saveToClipBoard(requireActivity(), binding.tvResultText.text.toString())
        }
        binding.ibShareText.setOnClickListener {
            startActivity(getShareTextIntent(binding.tvResultText.text.toString()))
        }
        codeScanner.startPreview()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA), 111)
        }
    }

    override fun onDestroyView() {
        _binding = null
        codeScanner.releaseResources()
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(RESULT_KEY, binding.tvResultText.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        showScanResult(savedInstanceState?.getString(RESULT_KEY))
    }

    private fun showScanResult(text: String?) {
        text ?: run {
            Log.d("QRCode Scanner", "no result from scan")
            return
        }
        binding.cvScanResultsCard.visibility = View.VISIBLE
        binding.tvResultText.text = text
    }

    companion object{
        const val RESULT_KEY = "RESULT_KEY"
    }
}