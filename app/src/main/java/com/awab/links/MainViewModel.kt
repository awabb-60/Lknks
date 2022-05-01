package com.awab.links

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awab.links.model.Repository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {

    val message = MutableLiveData("")

    val repository = Repository()
    fun makeShortLink(link:String) = viewModelScope.launch {

        val result:Deferred<String> = async { repository.shortenLink(link) }

        message.value = result.await()
        }

}