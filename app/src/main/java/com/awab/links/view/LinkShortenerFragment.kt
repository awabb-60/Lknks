package com.awab.links.view

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.awab.links.MainViewModel
import com.awab.links.R
import com.awab.links.databinding.LinkShortenerFragmentBinding

class LinkShortenerFragment : Fragment(R.layout.link_shortener_fragment) {

    private lateinit var mainViewModel: MainViewModel

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

        mainViewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MainViewModel::class.java]

        mainViewModel.message.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.shortLinkCard.visibility = View.VISIBLE
                binding.shortLink.text = it
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

        val linkText = binding.etLink.text.toString()

        binding.shorten.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            mainViewModel.makeShortLink(linkText)
        }

        binding.copyShortLink.setOnClickListener {
            val clipBoardManager =
                requireActivity().getSystemService(Activity.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("short link", binding.shortLink.text)
            clipBoardManager.setPrimaryClip(clip)

            Toast.makeText(requireContext(), "coped", Toast.LENGTH_SHORT).show()
        }

        binding.shareShortLink.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, binding.shortLink.text)
            }

            val chooser = Intent.createChooser(shareIntent, "share link")
            startActivity(chooser)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}