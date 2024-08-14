package com.oreo.status.hub.ui.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SavedViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is saved Fragment"
    }
    val text: LiveData<String> = _text
}