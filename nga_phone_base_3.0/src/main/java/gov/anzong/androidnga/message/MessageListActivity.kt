package gov.anzong.androidnga.message

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.paging.compose.collectAsLazyPagingItems
import com.alibaba.android.arouter.launcher.ARouter
import gov.anzong.androidnga.R
import gov.anzong.androidnga.activity.MessageDetailActivity
import gov.anzong.androidnga.arouter.ARouterConstants
import gov.anzong.androidnga.compose.message.MessageViewModel
import gov.anzong.androidnga.compose.theme.AppTheme
import gov.anzong.androidnga.compose.widget.PullRefreshColumn
import gov.anzong.androidnga.compose.widget.ScaffoldApp
import sp.phone.http.bean.MessageThreadPageInfo

class MessageListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                ScaffoldApp(this, appTitle = title.toString(), fabClick = { createNewMessage() }) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        MessageListView()
                    }
                }
            }
        }
    }

    private fun createNewMessage() {
        ARouter.getInstance().build(ARouterConstants.ACTIVITY_MESSAGE_POST)
            .withString("action", "new")
            .navigation(this)
    }

    @Composable
    fun MessageListView() {
        val newViewModel = ViewModelProvider(this)[MessageViewModel::class.java]
        val items = newViewModel.getMessageListData().collectAsLazyPagingItems()

        PullRefreshColumn(
            columnItem = { MessageListItem(messageInfo = it) },
            lazyPagingItems = items, onRefresh = { items.refresh() })

    }

    private fun startMessageDetail(messageInfo: MessageThreadPageInfo) {
        val mid = messageInfo.mid
        val intent = Intent()
        intent.putExtra("mid", mid)
        intent.setClass(this, MessageDetailActivity::class.java)
        startActivity(intent)
    }

    @Preview
    @Composable
    fun MessageListItem(messageInfo: MessageThreadPageInfo = MessageThreadPageInfo()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .clickable { startMessageDetail(messageInfo) }
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.default_avatar),
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(36.dp),
                contentDescription = null
            )

            var userName = messageInfo.from_username;
            if (TextUtils.isEmpty(userName)) {
                userName = "#SYSTEM#"
            }

            Column(modifier = Modifier.weight(2f)) {
                Text(text = userName, maxLines = 1, color = Color(0xFF545454), fontSize = 16.sp)
                Text(
                    messageInfo.subject,
                    Modifier.padding(top = 8.dp),
                    maxLines = 2,
                    color = Color(0xFF294563),
                    fontSize = 12.sp
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = messageInfo.time.split(" ")[0],
                    maxLines = 1,
                    color = Color(0xFF294563),
                    fontSize = 12.sp
                )
                Row(Modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 8.dp),
                        painter = painterResource(R.drawable.replies_icon),
                        contentDescription = null,
                    )
                    Text(
                        messageInfo.posts.toString(),
                        color = Color(0xFF294563),
                        fontSize = 12.sp
                    )
                }

            }

        }
    }
}

