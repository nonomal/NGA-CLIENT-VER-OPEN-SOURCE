package gov.anzong.androidnga.compose.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import com.justwen.androidnga.core.data.MessageThreadPageInfo

class MessageViewModel : ViewModel() {

    fun getMessageListData(): Flow<PagingData<com.justwen.androidnga.core.data.MessageThreadPageInfo>> {
        return MessageRepository.getMessageListData().cachedIn(viewModelScope)
    }
}