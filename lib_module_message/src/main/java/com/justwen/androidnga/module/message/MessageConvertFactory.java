package com.justwen.androidnga.module.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.justwen.androidnga.core.data.MessageArticlePageInfo;
import com.justwen.androidnga.core.data.MessageDetailInfo;
import com.justwen.androidnga.core.data.MessageListInfo;
import com.justwen.androidnga.core.data.MessageThreadPageInfo;

import java.util.ArrayList;
import java.util.List;

import gov.anzong.androidnga.base.util.ContextUtils;
import gov.anzong.androidnga.base.util.StringUtils;
import gov.anzong.androidnga.common.util.NLog;

/**
 * Created by Justwen on 2017/10/10.
 */

public class MessageConvertFactory {

    private String mErrorMsg = "";

    private static final String TAG = MessageConvertFactory.class.getSimpleName();

    public MessageListInfo getMessageListInfo(String js) {


        if (js == null || js.isEmpty()) {
            return null;
        }
        js = js.replaceAll("window.script_muti_get_var_store=", "");
        if (js.indexOf("/*error fill content") > 0)
            js = js.substring(0, js.indexOf("/*error fill content"));
        js = js.replaceAll("\"content\":\\+(\\d+),", "\"content\":\"+$1\",");
        js = js.replaceAll("\"subject\":\\+(\\d+),", "\"subject\":\"+$1\",");
        js = js.replaceAll("/\\*\\$js\\$\\*/", "");
        JSONObject o = null;
        try {
            o = (JSONObject) JSON.parseObject(js).get("data");
        } catch (Exception e) {
            NLog.e(TAG, "can not parse :\n" + js);
        }
        if (o == null) {
            try {
                o = (JSONObject) JSON.parseObject(js).get("error");
            } catch (Exception e) {
                NLog.e(TAG, "can not parse :\n" + js);
            }
            if (o == null) {
                mErrorMsg = "请重新登录";
            } else {
                mErrorMsg = o.getString("0");
                if (StringUtils.isEmpty(mErrorMsg))
                    mErrorMsg = "请重新登录";
            }
            return null;
        }

        MessageListInfo ret = new MessageListInfo();
        JSONObject o1 = (JSONObject) o.get("0");
        if (o1 == null) {
            mErrorMsg = "请重新登录";
            return null;
        }
        ret.set__nextPage(o1.getIntValue("nextPage"));
        ret.set__currentPage(o1.getIntValue("currentPage"));
        ret.set__rowsPerPage(o1.getIntValue("rowsPerPage"));


        List<MessageThreadPageInfo> messageEntryList = new ArrayList<>();
        JSONObject rowObj = (JSONObject) o1.get("0");
        for (int i = 1; rowObj != null; i++) {
            try {
                MessageThreadPageInfo entry = new MessageThreadPageInfo();
                entry.setMid(rowObj.getInteger("mid"));
                entry.setPosts(rowObj.getInteger("posts"));
                entry.setSubject(rowObj.getString("subject"));
                entry.setFrom_username(rowObj.getString("from_username"));
                entry.setLast_from_username(rowObj.getString("last_from_username"));
                int time = rowObj.getInteger("time");
                if (time > 0) {
                    entry.setTime(StringUtils.timeStamp2Date1(String.valueOf(time)));
                } else {
                    entry.setTime("");
                }
                time = rowObj.getIntValue("last_modify");
                if (time > 0) {
                    entry.setLastTime(StringUtils.timeStamp2Date1(String.valueOf(time)));
                } else {
                    entry.setLastTime("");
                }
                messageEntryList.add(entry);
                rowObj = (JSONObject) o1.get(String.valueOf(i));
            } catch (Exception e) {
                /*ThreadPageInfo entry = new ThreadPageInfo();
                String error = rowObj.getString("error");
				entry.setSubject(error);
				entry.setAuthor("");
				entry.setLastposter("");
				articleEntryList.add(entry);*/
            }
        }
        ret.setMessageEntryList(messageEntryList);
        return ret;
    }

    public String getErrorMsg() {
        return mErrorMsg;
    }





    public static int showImageQuality() {
        return 0;
//        if (isInWifi()) {
//            return 0;
//        } else {
//            return PhoneConfiguration.getInstance().imageQuality;
//        }
    }

    private void fillUserInfo(MessageArticlePageInfo row, JSONObject userInfoMap) {
        JSONObject userInfo = (JSONObject) userInfoMap.get(row.getFrom());
        if (userInfo == null) {
            return;
        }

        row.setAuthor(userInfo.getString("username"));
        row.setJs_escap_avatar(userInfo.getString("avatar"));
        row.setYz(userInfo.getString("yz"));
        row.setMute_time(userInfo.getString("mute_time"));
        row.setSignature(userInfo.getString("signature"));
    }

    private static String buildHeader(MessageArticlePageInfo row, String fgColorStr) {
        if (row == null || StringUtils.isEmpty(row.getSubject()))
            return "";
        StringBuilder sb = new StringBuilder();
        sb.append("<h4 style='color:").append(fgColorStr).append("' >")
                .append(row.getSubject()).append("</h4>");
        return sb.toString();
    }

}
