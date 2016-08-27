package com.rj.subscription.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.rj.subscription.R;
import com.rj.subscription.activity.SubscriptionInfoActivity;
import com.rj.subscription.adapter.SubscriptionGridViewAdapter;
import com.rj.subscription.bean.PubsubNode;
import com.rj.subscription.bean.PubsubNode;
import com.rj.subscription.database.DBManager;
import com.rj.subscription.view.MyGridView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lijunyan on 2016/8/23.
 */
public class SubscriptionFragment extends Fragment {

    private MyGridView mGridView;

    private SubscriptionGridViewAdapter mSubscriptionGridViewAdapter;

    private List<PubsubNode> dataSourceListInGridView;

    private View view;
    private DBManager dbManager;

    public final static String TAG = SubscriptionFragment.class.getName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_subscription, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");
        mGridView = (MyGridView) view.findViewById(R.id.subscription_gridview);

        //从本地数据库取
        dbManager = new DBManager(getActivity());
        dataSourceListInGridView = dbManager.queryAll();

        if (dataSourceListInGridView != null){
            for (PubsubNode subInfo : dataSourceListInGridView) {
                Log.i(TAG, "subInfo.getId() = " + subInfo.getId());
                Log.i(TAG, "subInfo.getName() = " + subInfo.getName());
                Log.i(TAG, "subInfo.getIsNotice() = " + subInfo.getIsNotice());
                Log.i(TAG, "subInfo.isPub() = " + subInfo.isPub());
            }
        }

        //给控件grid view设置数据源展示
        mSubscriptionGridViewAdapter = new SubscriptionGridViewAdapter(dataSourceListInGridView, getActivity());
        mGridView.setAdapter(mSubscriptionGridViewAdapter);

        //grid view 点击item 响应
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SubscriptionInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("SubscriptionInfo", dataSourceListInGridView.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //此处模拟从本地获取数据，实际应当向服务器请求数据
        startDownloadThread();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        //从本地数据库取最新的信息更新
        List<PubsubNode> datalist = dbManager.queryAll();
        dataSourceListInGridView.clear();
        dataSourceListInGridView.addAll(datalist);
        mSubscriptionGridViewAdapter.notifyDataSetChanged();
    }

    ////////////////////下载线程///////////////////////////////////////////////////////////////////

    private void startDownloadThread() {

        final String json = "{\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"appTypeData\": [\n" +
                "                {\n" +
                "                    \"appDesc\": \"办公类应用\",\n" +
                "                    \"appDynamixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appEntry\": \"http://www.baidu.com\",\n" +
                "                    \"appExpireTime\": \"2016-08-21 00:00:00\",\n" +
                "                    \"appFixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appIcon\": \"http://img.wdjimg.com/mms/icon/v1/8/10/1b26d9f0a258255b0431c03a21c0d108_512_512.png\",\n" +
                "                    \"appName\": \"随手记\",\n" +
                "                    \"appNoticeUrl\": null,\n" +
                "                    \"appPackage\": \"com.mymoney\",\n" +
                "                    \"appPower\": null,\n" +
                "                    \"appProperty\": null,\n" +
                "                    \"appSize\": \"15MB\",\n" +
                "                    \"appState\": \"1\",\n" +
                "                    \"appStatus\": \"enable\",\n" +
                "                    \"appType\": \"work\",\n" +
                "                    \"appUploadTime\": \"2016-07-21 00:00:00\",\n" +
                "                    \"appUrl\": \"http://wap.apk.anzhi.com/data2/apk/201607/21/8778ea7eb35b4b14a4bb1b79bc859d42_95518400.apk\",\n" +
                "                    \"appVersionCode\": \"412\",\n" +
                "                    \"appVersionName\": \"V4.1.2\",\n" +
                "                    \"id\": \"1\",\n" +
                "                    \"appMode\": \"1\"\n" +
                "                },\n" +


                "                {\n" +
                "                    \"appDesc\": \"办公类应用\",\n" +
                "                    \"appDynamixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appEntry\": \"http://www.baidu.com\",\n" +
                "                    \"appExpireTime\": \"2016-08-21 00:00:00\",\n" +
                "                    \"appFixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appIcon\": \"http://img.wdjimg.com/mms/icon/v1/2/bf/939a67b179e75326aa932fc476cbdbf2_512_512.png\",\n" +
                "                    \"appName\": \"搜狐\",\n" +
                "                    \"appNoticeUrl\": null,\n" +
                "                    \"appPackage\": \"com.sohu.sohuvideo\",\n" +
                "                    \"appPower\": null,\n" +
                "                    \"appProperty\": null,\n" +
                "                    \"appSize\": \"15MB\",\n" +
                "                    \"appState\": \"0\",\n" +
                "                    \"appStatus\": \"enable\",\n" +
                "                    \"appType\": \"work\",\n" +
                "                    \"appUploadTime\": \"2016-07-21 00:00:00\",\n" +
                "                    \"appUrl\": \"http://upgrade.m.tv.sohu.com/channels/hdv/5.0.0/SohuTV_5.0.0_47_201506112011.apk\",\n" +
                "                    \"appVersionCode\": \"412\",\n" +
                "                    \"appVersionName\": \"V4.1.2\",\n" +
                "                    \"id\": \"2\",\n" +
                "                    \"appMode\": \"1\"\n" +
                "                },\n" +


                "                {\n" +
                "                    \"appDesc\": \"办公类应用\",\n" +
                "                    \"appDynamixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appEntry\": \"http://www.baidu.com\",\n" +
                "                    \"appExpireTime\": \"2016-08-21 00:00:00\",\n" +
                "                    \"appFixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appIcon\": \"http://img.wdjimg.com/mms/icon/v1/8/10/1b26d9f0a258255b0431c03a21c0d108_512_512.png\",\n" +
                "                    \"appName\": \"腾讯视频\",\n" +
                "                    \"appNoticeUrl\": null,\n" +
                "                    \"appPackage\": \"com.tencent.qqlive\",\n" +
                "                    \"appPower\": null,\n" +
                "                    \"appProperty\": null,\n" +
                "                    \"appSize\": \"15MB\",\n" +
                "                    \"appState\": \"1\",\n" +
                "                    \"appStatus\": \"enable\",\n" +
                "                    \"appType\": \"work\",\n" +
                "                    \"appUploadTime\": \"2016-07-25 00:00:00\",\n" +
                "                    \"appUrl\": \"http://dldir1.qq.com/qqmi/TencentVideo_V4.1.0.8897_51.apk\",\n" +
                "                    \"appVersionCode\": \"412\",\n" +
                "                    \"appVersionName\": \"V4.1.2\",\n" +
                "                    \"id\": \"3\",\n" +
                "                    \"appMode\": \"1\"\n" +
                "                },\n" +



                "                {\n" +
                "                    \"appDesc\": \"办公类应用\",\n" +
                "                    \"appDynamixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appEntry\": \"http://www.baidu.com\",\n" +
                "                    \"appExpireTime\": \"2016-08-21 00:00:00\",\n" +
                "                    \"appFixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appIcon\": \"http://img.wdjimg.com/mms/icon/v1/d/f1/1c8ebc9ca51390cf67d1c3c3d3298f1d_512_512.png\",\n" +
                "                    \"appName\": \"网易云音乐\",\n" +
                "                    \"appNoticeUrl\": null,\n" +
                "                    \"appPackage\": \"com.netease.cloudmusic\",\n" +
                "                    \"appPower\": null,\n" +
                "                    \"appProperty\": null,\n" +
                "                    \"appSize\": \"15MB\",\n" +
                "                    \"appState\": \"1\",\n" +
                "                    \"appStatus\": \"enable\",\n" +
                "                    \"appType\": \"work\",\n" +
                "                    \"appUploadTime\": \"2016-07-25 00:00:00\",\n" +
                "                    \"appUrl\": \"http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk\",\n" +
                "                    \"appVersionCode\": \"412\",\n" +
                "                    \"appVersionName\": \"V4.1.2\",\n" +
                "                    \"id\": \"4\",\n" +
                "                    \"appMode\": \"1\"\n" +
                "                },\n" +



                "                {\n" +
                "                    \"appDesc\": \"办公类应用\",\n" +
                "                    \"appDynamixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appEntry\": \"http://www.baidu.com\",\n" +
                "                    \"appExpireTime\": \"2016-08-21 00:00:00\",\n" +
                "                    \"appFixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appIcon\": \"http://img.wdjimg.com/mms/icon/v1/3/89/9f5f869c0b6a14d5132550176c761893_512_512.png\",\n" +
                "                    \"appName\": \"UC浏览器\",\n" +
                "                    \"appNoticeUrl\": null,\n" +
                "                    \"appPackage\": \"com.UCMobile\",\n" +
                "                    \"appPower\": null,\n" +
                "                    \"appProperty\": null,\n" +
                "                    \"appSize\": \"15MB\",\n" +
                "                    \"appState\": \"1\",\n" +
                "                    \"appStatus\": \"enable\",\n" +
                "                    \"appType\": \"work\",\n" +
                "                    \"appUploadTime\": \"2016-07-25 00:00:00\",\n" +
                "                    \"appUrl\": \"http://wap3.ucweb.com/files/UCBrowser/zh-cn/999/UCBrowser_V10.6.0.620_android_pf145_(Build150721222435).apk\",\n" +
                "                    \"appVersionCode\": \"412\",\n" +
                "                    \"appVersionName\": \"V4.1.2\",\n" +
                "                    \"id\": \"5\",\n" +
                "                    \"appMode\": \"1\"\n" +
                "                },\n" +


                "                {\n" +
                "                    \"appDesc\": \"办公类应用\",\n" +
                "                    \"appDynamixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appEntry\": \"http://www.baidu.com\",\n" +
                "                    \"appExpireTime\": \"2016-08-21 00:00:00\",\n" +
                "                    \"appFixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appIcon\": \"http://img.wdjimg.com/mms/icon/v1/d/29/dc596253e9e80f28ddc84fe6e52b929d_512_512.png\",\n" +
                "                    \"appName\": \"360安全卫士\",\n" +
                "                    \"appNoticeUrl\": null,\n" +
                "                    \"appPackage\": \"com.qihoo360.mobilesafe\",\n" +
                "                    \"appPower\": null,\n" +
                "                    \"appProperty\": null,\n" +
                "                    \"appSize\": \"15MB\",\n" +
                "                    \"appState\": \"1\",\n" +
                "                    \"appStatus\": \"enable\",\n" +
                "                    \"appType\": \"work\",\n" +
                "                    \"appUploadTime\": \"2016-07-25 00:00:00\",\n" +
                "                    \"appUrl\": \"http://msoftdl.360.cn/mobilesafe/shouji360/360safesis/360MobileSafe_6.2.3.1060.apk\",\n" +
                "                    \"appVersionCode\": \"412\",\n" +
                "                    \"appVersionName\": \"V4.1.2\",\n" +
                "                    \"id\": \"6\",\n" +
                "                    \"appMode\": \"1\"\n" +
                "                },\n" +


                "                {\n" +
                "                    \"appDesc\": \"办公类应用\",\n" +
                "                    \"appDynamixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appEntry\": \"http://www.baidu.com\",\n" +
                "                    \"appExpireTime\": \"2016-08-21 00:00:00\",\n" +
                "                    \"appFixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appIcon\": \"http://img.wdjimg.com/mms/icon/v1/e/d0/03a49009c73496fb8ba6f779fec99d0e_512_512.png\",\n" +
                "                    \"appName\": \"51job\",\n" +
                "                    \"appNoticeUrl\": null,\n" +
                "                    \"appPackage\": \"com.job.android\",\n" +
                "                    \"appPower\": null,\n" +
                "                    \"appProperty\": null,\n" +
                "                    \"appSize\": \"15MB\",\n" +
                "                    \"appState\": \"0\",\n" +
                "                    \"appStatus\": \"enable\",\n" +
                "                    \"appType\": \"work\",\n" +
                "                    \"appUploadTime\": \"2016-07-21 00:00:00\",\n" +
                "                    \"appUrl\": \"http://www.51job.com/client/51job_51JOB_1_AND2.9.3.apk\",\n" +
                "                    \"appVersionCode\": \"412\",\n" +
                "                    \"appVersionName\": \"V4.1.2\",\n" +
                "                    \"id\": \"7\",\n" +
                "                    \"appMode\": \"1\"\n" +
                "                },\n" +


                "                {\n" +
                "                    \"appDesc\": \"办公类应用\",\n" +
                "                    \"appDynamixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appEntry\": \"http://www.baidu.com\",\n" +
                "                    \"appExpireTime\": \"2016-08-21 00:00:00\",\n" +
                "                    \"appFixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appIcon\": \"http://img.wdjimg.com/mms/icon/v1/f/29/cf90d1294ac84da3b49561a6f304029f_512_512.png\",\n" +
                "                    \"appName\": \"淘宝\",\n" +
                "                    \"appNoticeUrl\": null,\n" +
                "                    \"appPackage\": \"com.taobao.taobao\",\n" +
                "                    \"appPower\": null,\n" +
                "                    \"appProperty\": null,\n" +
                "                    \"appSize\": \"15MB\",\n" +
                "                    \"appState\": \"0\",\n" +
                "                    \"appStatus\": \"enable\",\n" +
                "                    \"appType\": \"work\",\n" +
                "                    \"appUploadTime\": \"2016-07-25 00:00:00\",\n" +
                "                    \"appUrl\": \"http://download.alicdn.com/wireless/taobao4android/latest/702757.apk\",\n" +
                "                    \"appVersionCode\": \"412\",\n" +
                "                    \"appVersionName\": \"V4.1.2\",\n" +
                "                    \"id\": \"8\",\n" +
                "                    \"appMode\": \"1\"\n" +
                "                },\n" +





                "                {\n" +
                "                    \"appDesc\": \"办公类应用\",\n" +
                "                    \"appDynamixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appEntry\": \"https://www.baidu.com\",\n" +
                "                    \"appExpireTime\": \"2016-08-21 00:00:00\",\n" +
                "                    \"appFixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appIcon\": \"http://img.wdjimg.com/mms/icon/v1/8/10/1b26d9f0a258255b0431c03a21c0d108_512_512.png\",\n" +
                "                    \"appName\": \"web测试应用\",\n" +
                "                    \"appNoticeUrl\": null,\n" +
                "                    \"appPackage\": \"com.sou.app\",\n" +
                "                    \"appPower\": null,\n" +
                "                    \"appProperty\": null,\n" +
                "                    \"appSize\": \"15MB\",\n" +
                "                    \"appState\": \"1\",\n" +
                "                    \"appStatus\": \"enable\",\n" +
                "                    \"appType\": \"work\",\n" +
                "                    \"appUploadTime\": \"2016-07-25 00:00:00\",\n" +
                "                    \"appUrl\": \"\",\n" +
                "                    \"appVersionCode\": \"412\",\n" +
                "                    \"appVersionName\": \"V4.1.2\",\n" +
                "                    \"id\": \"9\",\n" +
                "                    \"appMode\": \"2\"\n" +
                "                }\n" +



                "            ],\n" +
                "            \"appTypeName\": \"办公类\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"appTypeData\": [\n" +
                "                {\n" +
                "                    \"appDesc\": \"社交类应用\",\n" +
                "                    \"appDynamixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appEntry\": \"http://www.baidu.com\",\n" +
                "                    \"appExpireTime\": \"2016-08-21 00:00:00\",\n" +
                "                    \"appFixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appIcon\": \"http://img.wdjimg.com/mms/icon/v1/8/10/1b26d9f0a258255b0431c03a21c0d108_512_512.png\",\n" +
                "                    \"appName\": \"作业帮\",\n" +
                "                    \"appNoticeUrl\": null,\n" +
                "                    \"appPackage\": \"com.baidu.homework\",\n" +
                "                    \"appPower\": null,\n" +
                "                    \"appProperty\": null,\n" +
                "                    \"appSize\": \"15MB\",\n" +
                "                    \"appState\": \"1\",\n" +
                "                    \"appStatus\": \"enable\",\n" +
                "                    \"appType\": \"contact\",\n" +
                "                    \"appUploadTime\": \"2016-07-21 00:00:00\",\n" +
                "                    \"appUrl\": \"http://183.134.28.57/apk.r1.market.hiapk.com/data/upload/apkres/2016/7_21/14/com.baidu.homework_020504.apk?wsiphost=local\",\n" +
                "                    \"appVersionCode\": \"203\",\n" +
                "                    \"appVersionName\": \"V2.0.3\",\n" +
                "                    \"id\": \"10\",\n" +
                "                    \"appMode\": \"1\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"appTypeName\": \"社交类\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"appTypeData\": [\n" +


                "                {\n" +
                "                    \"appDesc\": \"办公类应用\",\n" +
                "                    \"appDynamixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appEntry\": \"http://www.baidu.com\",\n" +
                "                    \"appExpireTime\": \"2016-08-21 00:00:00\",\n" +
                "                    \"appFixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appIcon\": \"http://img.wdjimg.com/mms/icon/v1/7/08/2b3858e31efdee8a7f28b06bdb83a087_512_512.png\",\n" +
                "                    \"appName\": \"搜房网\",\n" +
                "                    \"appNoticeUrl\": null,\n" +
                "                    \"appPackage\": \"com.soufun.app\",\n" +
                "                    \"appPower\": null,\n" +
                "                    \"appProperty\": null,\n" +
                "                    \"appSize\": \"15MB\",\n" +
                "                    \"appState\": \"0\",\n" +
                "                    \"appStatus\": \"enable\",\n" +
                "                    \"appType\": \"work\",\n" +
                "                    \"appUploadTime\": \"2016-07-25 00:00:00\",\n" +
                "                    \"appUrl\": \"http://download.3g.fang.com/soufun_android_30001_7.9.0.apk\",\n" +
                "                    \"appVersionCode\": \"412\",\n" +
                "                    \"appVersionName\": \"V4.1.2\",\n" +
                "                    \"id\": \"11\",\n" +
                "                    \"appMode\": \"1\"\n" +
                "                },\n" +


                "                {\n" +
                "                    \"appDesc\": \"办公类应用\",\n" +
                "                    \"appDynamixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appEntry\": \"http://www.baidu.com\",\n" +
                "                    \"appExpireTime\": \"2016-08-21 00:00:00\",\n" +
                "                    \"appFixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appIcon\": \"http://img.wdjimg.com/mms/icon/v1/3/2d/dc14dd1e40b8e561eae91584432262d3_512_512.png\",\n" +
                "                    \"appName\": \"优酷\",\n" +
                "                    \"appNoticeUrl\": null,\n" +
                "                    \"appPackage\": \"com.youku.phone\",\n" +
                "                    \"appPower\": null,\n" +
                "                    \"appProperty\": null,\n" +
                "                    \"appSize\": \"15MB\",\n" +
                "                    \"appState\": \"1\",\n" +
                "                    \"appStatus\": \"enable\",\n" +
                "                    \"appType\": \"work\",\n" +
                "                    \"appUploadTime\": \"2016-07-21 00:00:00\",\n" +
                "                    \"appUrl\": \"http://dl.m.cc.youku.com/android/phone/Youku_Phone_youkuweb.apk\",\n" +
                "                    \"appVersionCode\": \"412\",\n" +
                "                    \"appVersionName\": \"V4.1.2\",\n" +
                "                    \"id\": \"12\",\n" +
                "                    \"appMode\": \"1\"\n" +
                "                },\n" +




                "                {\n" +
                "                    \"appDesc\": \"工具类应用\",\n" +
                "                    \"appDynamixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appEntry\": \"http://www.baidu.com\",\n" +
                "                    \"appExpireTime\": \"2016-08-21 00:00:00\",\n" +
                "                    \"appFixParam\": {\n" +
                "                        \"username\": \"aaaa\",\n" +
                "                        \"password\": \"2222\"\n" +
                "                    },\n" +
                "                    \"appIcon\": \"http://img.wdjimg.com/mms/icon/v1/8/10/1b26d9f0a258255b0431c03a21c0d108_512_512.png\",\n" +
                "                    \"appName\": \"WPS Office\",\n" +
                "                    \"appNoticeUrl\": null,\n" +
                "                    \"appPackage\": \"cn.wps.moffice_eng\",\n" +
                "                    \"appPower\": null,\n" +
                "                    \"appProperty\": null,\n" +
                "                    \"appSize\": \"15MB\",\n" +
                "                    \"appState\": \"1\",\n" +
                "                    \"appStatus\": \"enable\",\n" +
                "                    \"appType\": \"util\",\n" +
                "                    \"appUploadTime\": \"2016-07-21 00:00:00\",\n" +
                "                    \"appUrl\": \"http://wap.apk.anzhi.com/data2/apk/201607/22/32f4d7bb145c8f23eeb1c42a4f9b654d_34918100.apk\",\n" +
                "                    \"appVersionCode\": \"121\",\n" +
                "                    \"appVersionName\": \"V1.2.1\",\n" +
                "                    \"id\": \"13\",\n" +
                "                    \"appMode\": \"1\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"appTypeName\": \"工具类\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"resultCode\": \"success\"\n" +
                "}";


        new Thread(new Runnable() {
            @Override
            public void run() {
                //解析服务器返回来的数据
                final HashMap<String, List<PubsubNode>> serverDataHashMap = parseAppStoreJson(json);
                List<PubsubNode> mlist = hashMapToList(serverDataHashMap);
                Log.i(TAG,"Thread mlist.size = "+mlist.size());
                receiverFromDownloadThread(mlist);
            }
        }).start();

    }


    private HashMap<String, List<PubsubNode>> parseAppStoreJson(String appStoreJosn) {
        HashMap<String, List<PubsubNode>> hashMap = null;
        if (appStoreJosn != null) {
            if (appStoreJosn.length() > 0) {
                hashMap = new HashMap<>();
                try {
                    JSONObject jsonObject = new JSONObject(appStoreJosn);
                    String resultCode = jsonObject.getString("resultCode");
                    if (resultCode.equals("success")) {
                        JSONArray dataList = jsonObject.getJSONArray("data");
                        for (int i = 0; i < dataList.length(); i++) {
                            JSONObject appInfoTypeJO = dataList.getJSONObject(i);
                            String appTypeName = appInfoTypeJO.getString("appTypeName");
                            JSONArray appTypeDataList = appInfoTypeJO.getJSONArray("appTypeData");
                            List<PubsubNode> list = new ArrayList<>();
                            for (int j = 0; j < appTypeDataList.length(); j++) {
                                JSONObject appInfoJO = appTypeDataList.getJSONObject(j);
                                String subId = appInfoJO.getString("id");
                                String subName = appInfoJO.getString("appName");
                                String subIcon = appInfoJO.getString("appIcon");
                                String subDesc = appInfoJO.getString("appDesc");
                                String subNotice = appInfoJO.getString("appMode");
                                String subPub = appInfoJO.getString("appState");

                                PubsubNode subInfo = new PubsubNode();
                                Log.i(TAG,"subId = "+subId);
                                subInfo.setId(subId);
                                subInfo.setName(subName);
                                subInfo.setLogo(subIcon);
                                subInfo.setDescription(subDesc);
                                subInfo.setIsNotice(Integer.valueOf(subNotice));
                                subInfo.setPub(Integer.valueOf(subPub));
                                list.add(subInfo);
                            }
                            hashMap.put(appTypeName, list);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return hashMap;
    }

    //把一个hash map 转为一个 list
    public static List<PubsubNode> hashMapToList(HashMap hashMap) {
        List<PubsubNode> list = new ArrayList<>();
        if (hashMap != null) {
            for (int i = 0; i < hashMap.size(); i++) {
                String key = (String) hashMap.keySet().toArray()[i];
                List<PubsubNode> list1 = (List<PubsubNode>) hashMap.get(key);
                list.addAll(list1);
            }
        }
        return list;
    }


    ////////////////////////////////////数据处理更新线程///////////////////////////////////////////////////////

    private void receiverFromDownloadThread(final List<PubsubNode> serverDataList) {


        new Thread(new Runnable() {
            @Override
            public void run() {

                //删除本地数据库的存在的所有数据
                dbManager.deleteAll();

                //插入新的数据
                for (PubsubNode mySubscriptionNum : serverDataList) {
                    dbManager.insertSubscriptionNumInfo(mySubscriptionNum);
                }

                //刷新UI
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<PubsubNode> dbSubList = dbManager.queryAll();
                        SubscriptionFragment.this.dataSourceListInGridView.clear();
                        SubscriptionFragment.this.dataSourceListInGridView.addAll(dbSubList);
                        SubscriptionFragment.this.mSubscriptionGridViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
