package com.awab.links

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class LinkShortenerFragment: Fragment(R.layout.link_shortener_fragment){

    lateinit var mainViewModel:MainViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MainViewModel::class.java]

        val text: EditText = view.findViewById(R.id.link)
        val btnShorten: Button = view.findViewById(R.id.shorten)
        val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)

        btnShorten.setOnClickListener {
            Toast.makeText(context, "shortening ${text.text}", Toast.LENGTH_SHORT).show()

            progressBar.visibility = View.VISIBLE

            btnShorten.postDelayed({
                Toast.makeText(context, "result success", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }, 2000)
        }
    }
}