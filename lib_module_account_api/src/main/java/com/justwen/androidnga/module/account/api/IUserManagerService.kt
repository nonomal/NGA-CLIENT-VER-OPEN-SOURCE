package com.justwen.androidnga.module.account.api

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

interface IUserManagerService : IProvider {

    companion object {
        const val ROUTER_PATH = "/service/user"
    }

    fun showUserSwitchDialog(context: Context, callback: (() -> Unit)? = null)
}