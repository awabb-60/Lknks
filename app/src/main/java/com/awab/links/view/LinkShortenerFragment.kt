package com.awab.links.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.awab.links.MainViewModel
import com.awab.links.R
import com.awab.links.databinding.LinkShortenerFragmentBinding
import com.awab.links.utils.getShareTextIntent
import com.awab.links.utils.saveToClipBoard

class LinkShortenerFragment : Fragment(R.layout.link_shortener_fragment) {

    private lateinit var viewModel: MainViewModel

    private var _binding: LinkShortenerFragmentBinding? = null
    private val binding: LinkShortenerFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LinkShortenerFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireContext() as ViewModelStoreOwner)[MainViewModel::class.java]

        viewModel.shortLink.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.cvShortLinkCard.visibility = View.VISIBLE
                binding.tvShortLink.text = it
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

        val linkText = binding.etLink.text.toString()

        binding.btnShorten.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            viewModel.makeShortLink(linkText)
        }

        binding.ivCopy.setOnClickListener {
            saveToClipBoard(requireActivity(), binding.tvShortLink.text.toString())
        }
        binding.ivShare.setOnClickListener {
            startActivity(getShareTextIntent(binding.tvShortLink.text.toString()))
        }

        viewModel.createAppDir(requireContext(), requireActivity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}