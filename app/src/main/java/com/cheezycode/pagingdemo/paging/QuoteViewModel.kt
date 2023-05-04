package com.cheezycode.pagingdemo.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn

import com.cheezycode.pagingdemo.models.Result
import com.cheezycode.pagingdemo.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class QuoteViewModel @Inject constructor(private val repository: QuoteRepository) : ViewModel() {
    private val _pagingDataList = MutableStateFlow<DataState<PagingData<Result>>>(DataState.Loading)
    val pagerDataList: StateFlow<DataState<PagingData<Result>>> get() = _pagingDataList.asStateFlow()

    init {

        // fetchAllQuotesData()
    }  //

    val list = repository.getQuotes().cachedIn(viewModelScope).flowOn(Dispatchers.IO)

//    private fun fetchAllQuotesData() {
//        viewModelScope.launch {
//            repository.getQuotess()
//                .onEach {
//                    _pagingDataList.value= it
//                }.launchIn(viewModelScope)
//        }
//    }


    val  readToLocal=repository.readToLocal

    fun writeToLocal(name: String, email: String, age: Int, phone: String) = viewModelScope.launch {
        repository.writeToLocal(name, email, age, phone)
    }

}