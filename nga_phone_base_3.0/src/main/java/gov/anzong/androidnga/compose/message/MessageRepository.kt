package gov.anzong.androidnga.compose.message

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import gov.anzong.androidnga.compose.NetServiceKt
import kotlinx.coroutines.flow.Flow
import sp.phone.http.bean.MessageThreadPageInfo
import sp.phone.http.retrofit.RetrofitHelper
import sp.phone.mvp.model.convert.MessageConvertFactory

object MessageRepository {

    private const val PAGE_SIZE = 20

    fun getMessageListData(): Flow<PagingData<MessageThreadPageInfo>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { MessagePagingSource() }).flow
    }

}

class MessagePagingSource : PagingSource<Int, MessageThreadPageInfo>() {

    private val paramMap: HashMap<String, String> =
        hashMapOf("__lib" to "message", "__act" to "message", "act" to "list", "lite" to "js")

    override fun getRefreshKey(state: PagingState<Int, MessageThreadPageInfo>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MessageThreadPageInfo> {
        try {
            val page = params.key ?: 1
            val preKey = if (page > 1) page - 1 else null
            val netService =
                RetrofitHelper.getInstance().getService(NetServiceKt::class.java) as NetServiceKt
            paramMap["page"] = page.toString()
            val jsonString = netService.getString(paramMap)
            val factory = MessageConvertFactory()
            var nextKey: Int? = null
            val result = factory.getMessageListInfo(jsonString)?.let {
                nextKey = if (it.__nextPage > 0) it.__nextPage else null
                it.messageEntryList ?: emptyList()
            }

            if (!result.isNullOrEmpty()) {
                return LoadResult.Page(result, preKey, nextKey)
            } else {
                return LoadResult.Error(Exception())
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }

}
