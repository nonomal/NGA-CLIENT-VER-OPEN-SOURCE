package gov.anzong.androidnga.service

import android.content.Context
import androidx.appcompat.app.AlertDialog
import gov.anzong.androidnga.base.util.ToastUtils
import sp.phone.common.UserManagerImpl

class UserManagerService private constructor() {
    companion object {
        val instance = Holder.INSTANCE
    }

    private object Holder {
        val INSTANCE = UserManagerService()
    }

    fun showUserSwitchDialog(context: Context, callback: (() -> Unit)? = null) {
        val users = UserManagerImpl.getInstance().userList
        if (users.isNullOrEmpty()) {
            return
        }
        val index = UserManagerImpl.getInstance().activeUserIndex
        val items = Array<CharSequence>(users.size) { i -> users[i].mNickName }

        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setSingleChoiceItems(
            items, index
        ) { dialog, which ->
            run {
                UserManagerImpl.getInstance().setActiveUser(which)
                ToastUtils.info(
                    "切换账户成功,当前账户名:" + items[index]
                )
                dialog.dismiss()
                callback?.invoke()
            }
        }.setTitle("切换账号")
        dialogBuilder.show()
    }

}