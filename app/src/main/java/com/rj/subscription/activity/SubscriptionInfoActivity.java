package com.rj.subscription.activity;


import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rj.subscription.R;
import com.rj.subscription.bean.PubsubNode;
import com.rj.subscription.database.DBManager;
import com.rj.subscription.util.ToastTool;

/**
 * Created by lijunyan on 2016/8/23.
 */
public class SubscriptionInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private PubsubNode subInfo;
    private DBManager dbManager;
    private ImageView subIconIV;
    private TextView subNameTV;
    private TextView subDescTV;
    private CheckBox subIsNoticeCB;
    private Button intoSubscriptionBtn;
    private RelativeLayout subNoticeBar;
    private RelativeLayout subMessageRecordBar;

    public final static String TAG = SubscriptionInfoActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_info);
        toolbar = (Toolbar) findViewById(R.id.setting_subscription_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        dbManager = new DBManager(this);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        subInfo = (PubsubNode) getIntent().getSerializableExtra("SubscriptionInfo");

        subIconIV = (ImageView) findViewById(R.id.setting_sub_icon_iv);
        subDescTV = (TextView) findViewById(R.id.setting_sub_desc_tv);
        subNameTV = (TextView) findViewById(R.id.setting_sub_name_tv);
        subIsNoticeCB = (CheckBox) findViewById(R.id.setting_sub_newnotive_cb);
        intoSubscriptionBtn = (Button) findViewById(R.id.setting_sub_into_btn);
        subMessageRecordBar = (RelativeLayout) findViewById(R.id.setting_sub_message_recored_rl);
        subNoticeBar = (RelativeLayout) findViewById(R.id.setting_sub_newnotice_rl);

        initView(subInfo);
    }

    private void initView(final PubsubNode subInfo) {
        Log.i(TAG, "initView");
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.app_add_icon_normal)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(subInfo.getLogo(), subIconIV, options);
        subNameTV.setText(subInfo.getName());
        subDescTV.setText(subInfo.getDescription());
        Log.i(TAG, "set befor  subInfo.getIsNotice() = " + subInfo.getIsNotice());
        if (subInfo.getIsNotice() == 1) {
            subIsNoticeCB.setChecked(true);
        } else {
            subIsNoticeCB.setChecked(false);
        }

        if (subInfo.isPub() == 1) {
            //已订阅
            subNoticeBar.setVisibility(View.VISIBLE);
            subMessageRecordBar.setVisibility(View.VISIBLE);
        } else {
            //未订阅
            subNoticeBar.setVisibility(View.GONE);
            subMessageRecordBar.setVisibility(View.GONE);
        }

        if (subInfo.isPub() == 1) {
            intoSubscriptionBtn.setText("进入公众号");
        } else {
            intoSubscriptionBtn.setText("关注");
        }

        subNoticeBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subIsNoticeCB.isChecked()) {
                    subIsNoticeCB.setChecked(false);
                } else {
                    subIsNoticeCB.setChecked(true);
                }
            }
        });

        subIsNoticeCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    subInfo.setIsNotice(1);
                    //更新数据库中Notice的状态
                    dbManager.updateSubscriptionNumInfo(subInfo);
                } else {
                    subInfo.setIsNotice(0);
                    //更新数据库中Notice的状态
                    dbManager.updateSubscriptionNumInfo(subInfo);
                }
            }
        });

        //"关注"或者"进入公众号"button
        intoSubscriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intoSubscriptionBtn.getText().equals("关注")) {
                    //订阅成功
                    ToastTool.show(getApplicationContext(), "关注成功", Toast.LENGTH_SHORT);
                    subInfo.setPub(1);
                    subInfo.setIsNotice(0);
                    Log.i(TAG, "subInfo.isPub() = " + subInfo.isPub());
                    Log.i(TAG, "subInfo.getId() = " + subInfo.getId());
                    //向本地数据库更新订阅状态
                    dbManager.updateSubscriptionNumInfo(subInfo);
                    //更新UI
                    initView(subInfo);
                } else {
                    //进入公众号
                }
            }
        });
    }

    //////////////////////////////menu///////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.sub_no_longer_focus:
                    //取消关注成功
                    ToastTool.show(getApplicationContext(), "取消关注成功", Toast.LENGTH_SHORT);
                    //向本地数据库更新订阅状态
                    subInfo.setPub(0);
                    subInfo.setIsNotice(0);
                    dbManager.updateSubscriptionNumInfo(subInfo);
                    //更新UI
                    initView(subInfo);
                    break;
                case R.id.sub_delete:
                    msg += "Click sub_delete";
                    break;
                case R.id.sub_cancel:
                    msg += "Click sub_cancel";
                    break;
            }
            if (!msg.equals("")) {
                Toast.makeText(SubscriptionInfoActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_subscription, menu);
        return true;
    }

    //更新菜单的显示与隐藏
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (subInfo.isPub() == 1) {
            menu.findItem(R.id.sub_no_longer_focus).setVisible(true);
            menu.findItem(R.id.sub_cancel).setVisible(true);
            menu.findItem(R.id.sub_delete).setVisible(true);
        } else {
            menu.findItem(R.id.sub_no_longer_focus).setVisible(false);
            menu.findItem(R.id.sub_cancel).setVisible(false);
            menu.findItem(R.id.sub_delete).setVisible(false);
        }
        invalidateOptionsMenu();
        return true;
    }
}
