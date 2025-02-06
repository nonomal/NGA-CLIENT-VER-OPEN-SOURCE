package com.justwen.androidnga.module.message.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class MessageViewModel : ViewModel() {

    fun getMessageListData(): Flow<PagingData<com.justwen.androidnga.core.data.MessageThreadPageInfo>> {
        return MessageRepository.getMessageListData().cachedIn(viewModelScope)
    }
}