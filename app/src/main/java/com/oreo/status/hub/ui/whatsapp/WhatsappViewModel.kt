package com.oreo.status.hub.ui.whatsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WhatsappViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is whatsapp Fragment"
    }
    val text: LiveData<String> = _text
}