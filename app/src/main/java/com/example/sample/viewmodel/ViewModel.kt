package com.example.sample.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sample.model.DataModel

class ViewModel : ViewModel() {


    // Create MutableLiveData which MainFragment can subscribe to
    // When this data changes, it triggers the UI to do an update
    val status = MutableLiveData<DataModel>()

    // Get the updated text from our model and post the value to MainFragment
    fun getUpdatedText() : String {
        return  status.value.toString()
    }

    fun updateStatusText(status : String){
        this.status.postValue(DataModel(status))
    }

}