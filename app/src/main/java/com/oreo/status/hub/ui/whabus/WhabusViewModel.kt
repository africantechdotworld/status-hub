package com.oreo.status.hub.ui.whabus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WhabusViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is whabus Fragment"
    }
    val text: LiveData<String> = _text
}