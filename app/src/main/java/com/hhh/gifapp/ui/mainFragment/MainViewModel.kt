package com.hhh.gifapp.ui.mainFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.hhh.gifapp.data.api.GifPagingSource
import com.hhh.gifapp.data.api.GifRepository
import com.hhh.gifapp.data.api.GifServise
import com.hhh.gifapp.model.GifData
import com.hhh.gifapp.model.GifResponse
import com.hhh.gifapp.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: GifRepository): ViewModel() {

    val gifLiveData: MutableLiveData<State<GifResponse>> = MutableLiveData()

//    init {
//        getGifs("")
//    }
//
//    fun getGifs(query: String) =
//        viewModelScope.launch {
//            gifLiveData.postValue(State.Loading())
//            val response = repository.getGifs(query = query)
//            if (response.isSuccessful) {
//                response.body().let {
//                    gifLiveData.postValue(State.Success(it))
//                }
//            } else {
//                gifLiveData.postValue(State.Error(message = response.message()))
//            }
//        }


    suspend fun getPagingGifs(query: String) =
        repository.getPagingGifs(query)
}