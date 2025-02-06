package gov.anzong.androidnga.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.justwen.androidnga.base.activity.ARouterConstants;

import gov.anzong.androidnga.R;
import sp.phone.ui.fragment.MessageDetailFragment;
import sp.phone.util.StringUtils;

@Route(path = ARouterConstants.ACTIVITY_MESSAGE_DETAIL)
public class MessageDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setToolbarEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        setupToolbar();
        initFragment();
    }

    private void initFragment() {
        Fragment fragment = new MessageDetailFragment();
        fragment.setHasOptionsMenu(true);
        Bundle bundle = new Bundle();
        String url = getIntent().getDataString();
        int mid;
        if (null != url) {
            mid = StringUtils.getUrlParameter(url, "mid");
        } else {
            mid = getIntent().getIntExtra("mid", 0);
        }
        bundle.putInt("mid", mid);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

}